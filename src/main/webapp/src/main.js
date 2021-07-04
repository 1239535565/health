import Vue from 'vue'

import 'normalize.css/normalize.css'// A modern alternative to CSS resets

import Element from 'element-ui'
import 'element-ui/lib/theme-chalk/index.css'
import '@/styles/penint.scss' // penint css

import '@/styles/index.scss' // global css

import App from './App'
import router from './router'
import store from './store'
import VideoPlayer from 'vue-video-player'
require('video.js/dist/video-js.css')
require('vue-video-player/src/custom-theme.css')
Vue.use(VideoPlayer)

import './icons' // icon
import './permission' // permission control
// import './mock' // simulation data
import { parseTime, resetForm, addDateRange, selectDictLabel, download } from "@/utils/penint";

import Pagination from "@/components/Pagination";

Vue.prototype.parseTime = parseTime
Vue.prototype.resetForm = resetForm
Vue.prototype.addDateRange = addDateRange
Vue.prototype.selectDictLabel = selectDictLabel
Vue.prototype.download = download

// 全局组件挂载
Vue.component('Pagination', Pagination)

Vue.use(Element, {
  size: 'medium' // set element-ui default size
})

Vue.config.productionTip = false

new Vue({
  el: '#app',
  router,
  store,
  template: '<App/>',
  components: { App }
})
