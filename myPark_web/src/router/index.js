import Vue from 'vue'
import Router from 'vue-router'
import Login from '@/views/Login'
import UserIndex from '@/views/User/index'
Vue.use(Router)

export default new Router({
  routes: [
    {
      path: '/',
      name: 'Login',
      component: Login
    },
    {
      path: '/user/index',
      name: 'index',
      component: UserIndex
    }
  ]
})
