import Vue from 'vue'
import App from './App.vue'

import store from './store'
import ElementUI from 'element-ui'
import 'element-ui/lib/theme-chalk/index.css'

// 导入自定义样式
import '@/assets/css/index.css'
// 引入
import axios from 'axios'
// 引入echarts
import echarts from 'echarts'

import BMap from 'BMap'

Vue.prototype.$echarts = echarts

// 给Vue原型挂载一个属性
Vue.prototype.$axios = axios
import router from './router'
Vue.use(ElementUI)
Vue.config.productionTip = false

new Vue({
  router,
  store,
  axios,
  render: h => h(App)
}).$mount('#app')

