import Vue from 'vue'
import Vuex from 'vuex'
import configure from './configure'
import user from './user'
import food from './food'


Vue.use(Vuex)

const store = new Vuex.Store({
  modules: {
    configure,
    user,
    food
  }
})

export default store
