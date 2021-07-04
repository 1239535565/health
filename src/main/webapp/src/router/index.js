import Vue from 'vue'
import Router from 'vue-router'
const _import = require('./_import_' + process.env.NODE_ENV)
// in development-env not use lazy-loading, because lazy-loading too many pages will cause webpack hot update too slow. so only in production use lazy-loading;
// detail: https://gansuyuanzhan.github.io/GanSuYuanZhan-site/#/lazy-loading

Vue.use(Router)

/* Layout */
import Layout from '../views/layout/Layout'

/** note: submenu only apppear when children.length>=1
*   detail see  https://gansuyuanzhan.github.io/GanSuYuanZhan-site/#/router-and-nav?id=sidebar
**/

/**
* hidden: true                   if `hidden:true` will not show in the sidebar(default is false)
* alwaysShow: true               if set true, will always show the root menu, whatever its child routes length
*                                if not set alwaysShow, only more than one route under the children
*                                it will becomes nested mode, otherwise not show the root menu
* redirect: noredirect           if `redirect:noredirect` will no redirct in the breadcrumb
* name:'router-name'             the name is used by <keep-alive> (must set!!!)
* meta : {
    roles: ['admin','editor']     will control the page roles (you can set multiple roles)
    title: 'title'               the name show in submenu and breadcrumb (recommend set)
    icon: 'svg-name'             the icon show in the sidebar,
    noCache: true                if fasle ,the page will no be cached(default is false)
  }
**/
export const constantRouterMap = [
  { path: '/login', component: _import('login/index'), hidden: true },
  { path: '/registerDoctor', component: _import('login/registerDoctor'), hidden: true },
  { path: '/registerPatients', component: _import('login/registerPatients'), hidden: true },
  { path: '/forgotPassword', component: _import('login/forgotPassword'), hidden: true },
  { path: '/404', component: _import('error/404'), hidden: true },
  { path: '/401', component: _import('error/401'), hidden: true },
  {
    path: '/user',
    component: Layout,
    hidden: true,
    redirect: 'noredirect',
    children: [
      {
        path: 'profile',
        component: () => import('@/views/systemUser/profile/index'),
        name: 'Profile',
        meta: { title: '个人中心', icon: 'user' }
      }
    ]
  },
  { path: '/invoice/index/:id', component: _import('invoice/index'), hidden: true }
]

export default new Router({
  // mode: 'history', // require service support
  scrollBehavior: () => ({ y: 0 }),
  routes: constantRouterMap
})

export const asyncRouterMap = [
  {
    path: '',
    component: Layout,
    redirect: 'user/adminUser',
    children: [
      {
        path: 'user/adminUser',
        component: _import('systemUser/index'),
        name: 'adminUser',
        meta: { title: '管理员列表', noCache: true, icon: 'user' }
      }]
  },
  {
    path: '/user/doctorUser',
    component: Layout,
    redirect: 'noredirect',
    children: [
      {
        path: 'index',
        component: _import('user/doctor/index'),
        name: 'doctorUser',
        meta: { title: '医生列表', noCache: true, icon: 'user' }
      }]
  },
  {
    path: '/user/patiens',
    component: Layout,
    redirect: 'noredirect',
    children: [
      {
        path: 'index',
        component: _import('user/patiens/index'),
        name: 'patiensUser',
        meta: { title: '患者列表', noCache: true, icon: 'user' }
      }]
  },
  {
    path: '/department',
    component: Layout,
    redirect: 'noredirect',
    children: [
      {
        path: 'index',
        component: _import('hospital/department/index'),
        name: 'department',
        meta: { title: '科室管理', noCache: true, icon: 'user' }
      }]
  },
  { path: '*', redirect: '/404', hidden: true }
]

export const nasyncRouterMap = [
  // {
  //   path: '',
  //   component: Layout,
  //   redirect: 'user',
  //   children: [{
  //     path: 'user',
  //     component: _import('systemUser/index'),
  //     name: 'user',
  //     meta: { title: '员工管理', icon: 'user', noCache: true }
  //   }]
  // },
  {
    path: '',
    component: Layout,
    redirect: 'business/registration/index',
    children: [
      {
        path: 'business/registration/index',
        component: _import('business/registration/index'),
        name: 'registrationIndex',
        meta: { title: '挂号列表', noCache: true, icon: 'user' }
      }]
  },
  {
    path: '/business/questions',
    component: Layout,
    redirect: 'noredirect',
    children: [
      {
        path: 'index',
        component: _import('business/questions/index'),
        name: 'questionsIndex',
        meta: { title: '问答系统', noCache: true, icon: 'user' }
      }]
  },
  { path: '*', redirect: '/404', hidden: true }
]

export const tasyncRouterMap = [
  {
    path: '',
    component: Layout,
    redirect: 'user/doctorUser/view',
    children: [{
      path: 'user/doctorUser/view',
      component: _import('user/doctor/view'),
      name: 'user',
      meta: { title: '医生列表', icon: 'user', noCache: true }
    }]
  },
  {
    path: '/business/registration',
    component: Layout,
    redirect: 'noredirect',
    children: [
      {
        path: 'my/index',
        component: _import('business/registration/my/index'),
        name: 'registrationMy',
        meta: { title: '我要挂号', noCache: true, icon: 'user' }
      }]
  },
  {
    path: '/business/registration',
    component: Layout,
    redirect: 'noredirect',
    children: [
      {
        path: 'patientsView',
        component: _import('business/registration/patientsView'),
        name: 'department',
        meta: { title: '挂号列表', noCache: true, icon: 'user' }
      }]
  },
  {
    path: '',
    component: Layout,
    redirect: 'case/list',
    children: [
      {
        path: 'case/list',
        component: _import('case/list/index'),
        name: 'case',
        meta: { title: '病例管理', noCache: true, icon: 'user' }
      }]
  },
  {
    path: '/business/questions',
    component: Layout,
    redirect: 'noredirect',
    children: [
      {
        path: 'my',
        component: _import('business/questions/my'),
        name: 'department',
        meta: { title: '我要问医生', noCache: true, icon: 'user' }
      }]
  },
  {
    path: '/business/questions',
    component: Layout,
    redirect: 'noredirect',
    children: [
      {
        path: 'patientsView',
        component: _import('business/questions/patientsView'),
        name: 'department',
        meta: { title: '医生回答', noCache: true, icon: 'user' }
      }]
  },
  { path: '*', redirect: '/404', hidden: true }
]
