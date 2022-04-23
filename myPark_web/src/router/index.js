import Vue from 'vue'
import Router from 'vue-router'
import Login from '@/views/Login'
import UserIndex from '@/views/User/index'
import adminIndex from '@/views/admin/index'
Vue.use(Router)

export default new Router({
  routes: [
    {
      path: '/',
      name: 'Login',
      component: Login
    },
    // {
    //   path: '/user/register',
    //   name: 'Register',
    //   component: Register
    // },
    {
      path: '/user/index',
      name: 'index',
      component: UserIndex
    },
    {
      path: '/admin/index',
      name: 'adminIndex',
      component: adminIndex
    }
  ]
})
