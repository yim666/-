// The Vue build version to load with the `import` command
// (runtime-only or standalone) has been set in webpack.base.conf with an alias.
import Vue from 'vue'
import App from './App'
import router from './router'
import axios from 'axios'
import VueCookie from 'vue-cookies'
// 使用ant-design-vue
import Antd from 'ant-design-vue'
import 'ant-design-vue/dist/antd.css'
Vue.use(Antd)

// Vue.use(axios) 不建议使用，如下写法最好
Vue.prototype.$axios = axios

Vue.use(VueCookie)

Vue.config.productionTip = false

/* eslint-disable no-new */
new Vue({
  el: '#app',
  router,
  // render??
  render: h => h(App),
  components: { App },
  template: '<App/>'
})
