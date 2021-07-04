<template>
  <el-breadcrumb class="app-breadcrumb" separator="/">
    <transition-group name="breadcrumb">
      <el-breadcrumb-item v-for="(item,index)  in levelList" :key="item.path" v-if='item.meta.title'>
        <span v-if='item.redirect==="noredirect"||index==levelList.length-1' class="no-redirect">{{storeName + item.meta.title}}</span>
        <router-link v-else :to="item.redirect||item.path">{{item.meta.title}}</router-link>
      </el-breadcrumb-item>
    </transition-group>
  </el-breadcrumb>
</template>



<script>
  import { getUserInfo } from '@/api/login'

  export default {
    created() {
      this.getBreadcrumb()
      this.getAll()
    },
    data() {
      return {
        levelList: null,
        storeName: ''
      }
    },
    watch: {
      $route() {
        this.getBreadcrumb()
      }
    },
    methods: {
      getBreadcrumb() {
        const matched = this.$route.matched.filter(item => item.name)
        this.levelList = matched
      },
      getAll() {
        getUserInfo().then(response => {
          if (response.data.data.storeName !== undefined) {
            this.storeName = response.data.data.storeName
          }
        })
      }
    }
}
</script>

<style rel="stylesheet/scss" lang="scss" scoped>
  .app-breadcrumb.el-breadcrumb {
    display: inline-block;
    font-size: 14px;
    line-height: 50px;
    margin-left: 10px;
    .no-redirect {
      color: #97a8be;
      cursor: text;
    }
  }
</style>
