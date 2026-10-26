import SignUp from '@/pages/SignUp'
import {SignUp as register, getRegistrationCaptcha} from '@/api/index'

jest.mock('@/api/index', () => ({
  SignUp: jest.fn(),
  getRegistrationCaptcha: jest.fn()
}))
jest.mock('@/mixins', () => ({mixin: {}}))

const flush = async () => {
  for (let i = 0; i < 10; i++) {
    await Promise.resolve()
  }
}

function registrationForm () {
  const vm = Object.assign(SignUp.data(), {
    notify: jest.fn(),
    $refs: {registerForm: {validate: onValidated => onValidated(true)}},
    $router: {push: jest.fn()}
  })
  Object.keys(SignUp.methods).forEach(key => {
    vm[key] = SignUp.methods[key].bind(vm)
  })
  vm.registerForm.birth = new Date(2000, 0, 1)
  return vm
}

describe('registration captcha', () => {
  beforeEach(() => {
    jest.clearAllMocks()
    getRegistrationCaptcha.mockResolvedValue({code: 1, captchaId: 'new-id', image: 'data:image/png;base64,test'})
  })

  it('loads a fresh image and clears the previous answer', async () => {
    const vm = registrationForm()
    vm.registerForm.captchaCode = 'OLD12'
    await vm.refreshCaptcha()
    expect(vm.captchaId).toBe('new-id')
    expect(vm.captchaImage).toBe('data:image/png;base64,test')
    expect(vm.registerForm.captchaCode).toBe('')
    expect(vm.captchaLoading).toBe(false)
  })

  it('keeps submission disabled when image loading fails', async () => {
    const vm = registrationForm()
    getRegistrationCaptcha.mockRejectedValue(new Error('offline'))
    await vm.refreshCaptcha()
    vm.submitRegister()
    expect(vm.captchaId).toBe('')
    expect(vm.captchaLoading).toBe(false)
    expect(register).not.toHaveBeenCalled()
    expect(vm.notify).toHaveBeenCalled()
  })

  it('refreshes the consumed captcha and displays the backend error', async () => {
    const vm = registrationForm()
    vm.captchaId = 'old-id'
    vm.registerForm.captchaCode = 'WRONG'
    register.mockResolvedValue({code: 0, msg: '验证码错误或已过期，请刷新后重试'})
    vm.submitRegister()
    expect(register.mock.calls[0][0].get('captchaId')).toBe('old-id')
    expect(register.mock.calls[0][0].get('captchaCode')).toBe('WRONG')
    await flush()
    expect(vm.notify).toHaveBeenCalledWith('验证码错误或已过期，请刷新后重试', 'error')
    expect(vm.captchaId).toBe('new-id')
    expect(vm.registerForm.captchaCode).toBe('')
    expect(vm.submitting).toBe(false)
  })

  it('refreshes after a network failure and allows retry', async () => {
    const vm = registrationForm()
    vm.captchaId = 'old-id'
    register.mockRejectedValue(new Error('offline'))
    vm.submitRegister()
    await flush()
    expect(vm.submitting).toBe(false)
    expect(vm.captchaId).toBe('new-id')
  })

  it('prevents duplicate submissions while registration is pending', async () => {
    const vm = registrationForm()
    vm.captchaId = 'id'
    register.mockImplementation(() => new Promise(() => {}))
    vm.submitRegister()
    vm.submitRegister()
    expect(register).toHaveBeenCalledTimes(1)
  })

  it('navigates home after success without reusing the captcha', async () => {
    jest.useFakeTimers()
    const vm = registrationForm()
    vm.captchaId = 'id'
    register.mockResolvedValue({code: 1})
    vm.submitRegister()
    await flush()
    vm.submitRegister()
    expect(register).toHaveBeenCalledTimes(1)
    expect(getRegistrationCaptcha).not.toHaveBeenCalled()
    jest.runAllTimers()
    expect(vm.$router.push).toHaveBeenCalledWith({path: '/'})
    jest.useRealTimers()
  })
})
