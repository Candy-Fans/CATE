import Vue from 'vue'
import Comment from '@/components/Comment'
import {getAllComment, getUserOfId} from '@/api/index'

jest.mock('@/api/index', () => ({
  setComment: jest.fn(),
  setLike: jest.fn(),
  getAllComment: jest.fn(),
  getUserOfId: jest.fn()
}))
jest.mock('@/mixins', () => ({
  mixin: {
    methods: {
      attachImageUrl: path => path || '/img/user.jpg',
      notify: jest.fn()
    }
  }
}))

const comments = [
  {id: 122, userId: 44, content: '好', createTime: '2024-05-24', up: 0},
  {id: 141, userId: 53, content: '3', createTime: '2026-10-26', up: 0},
  {id: 142, userId: 53, content: '123456', createTime: '2026-10-26', up: 0}
]
const users = {
  44: {username: '6', avator: '/6.jpg'},
  53: {username: 'a', avator: '/a.jpg'}
}

function deferred () {
  let resolvePromise
  const promise = new Promise(resolve => { resolvePromise = resolve })
  return {promise, resolve: resolvePromise}
}

async function flush () {
  for (let i = 0; i < 20; i++) {
    await Promise.resolve()
  }
  await Vue.nextTick()
}

function rows (vm) {
  return Array.from(vm.$el.querySelectorAll('.popular')).map(row => ({
    content: row.querySelector('.content').textContent,
    username: row.querySelector('.name').textContent,
    image: row.querySelector('.popular-img img').getAttribute('src')
  }))
}

describe('comment author display', () => {
  let vm

  beforeEach(() => {
    jest.resetAllMocks()
    getAllComment.mockResolvedValue(comments)
    getUserOfId.mockImplementation(id => Promise.resolve(users[id]))
    const Constructor = Vue.extend(Object.assign({}, Comment, {
      computed: {loginIn: () => true, userId: () => 53, avator: () => '/a.jpg', id: () => 33},
      components: {
        'el-input': {render: h => h('textarea')},
        'el-button': {render (h) { return h('button', this.$slots.default) }}
      }
    }))
    vm = new Constructor({propsData: {type: 0, playId: 33}})
  })

  afterEach(() => { vm.$destroy() })

  it('keeps authors attached to their comments when user requests finish out of order', async () => {
    const oldUser = deferred()
    const newUser = deferred()
    getUserOfId.mockImplementation(id => id === 44 ? oldUser.promise : newUser.promise)
    vm.$mount()
    await flush()
    newUser.resolve(users[53])
    await flush()
    oldUser.resolve(users[44])
    await flush()
    expect(rows(vm)).toEqual([
      {content: '好', username: '6', image: '/6.jpg'},
      {content: '3', username: 'a', image: '/a.jpg'},
      {content: '123456', username: 'a', image: '/a.jpg'}
    ])
  })

  it('replaces author associations after posting or liking refreshes the list', async () => {
    vm.$mount()
    await flush()
    getAllComment.mockResolvedValue([comments[2], comments[0], comments[1]])
    vm.getComment()
    await flush()
    expect(rows(vm).map(row => [row.content, row.username])).toEqual([
      ['123456', 'a'], ['好', '6'], ['3', 'a']
    ])
  })

  it('does not shift authors when an individual user request fails', async () => {
    getUserOfId.mockImplementation(id => id === 44
      ? Promise.reject(new Error('unavailable')) : Promise.resolve(users[id]))
    vm.$mount()
    await flush()
    const displayed = rows(vm)
    expect(displayed[0].username).toBe('用户信息暂不可用')
    expect(displayed[1].username).toBe('a')
    expect(displayed[2].username).toBe('a')
  })

  it('does not let a slower earlier list overwrite a newer refresh', async () => {
    const earlier = deferred()
    getAllComment.mockReturnValueOnce(earlier.promise)
    vm.$mount()
    getAllComment.mockResolvedValue([comments[2]])
    vm.getComment()
    await flush()
    earlier.resolve([comments[0]])
    await flush()
    expect(rows(vm).map(row => [row.content, row.username])).toEqual([['123456', 'a']])
  })

  it('loads the new target when the same component is reused for another album', async () => {
    vm.$mount()
    await flush()
    getAllComment.mockResolvedValue([])
    vm.playId = 34
    await flush()
    expect(getAllComment).toHaveBeenLastCalledWith(0, 34)
    expect(rows(vm)).toEqual([])
  })
})
