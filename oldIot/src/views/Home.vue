<template>
  <el-container class="home" direction="vertical">
    <!-- header -->
    <nav-top></nav-top>
    <!-- 下边 -->
    <el-container class="home-content" direction="vertical">
     <el-row>
      <el-col :span="4">
        <!-- 左侧导航栏 -->
        <slider></slider>
      </el-col>
      <el-col :span="20" class="pl">
        <!-- 内容区 -->
        <div class="app-wrap" >
            <!-- 此处放置el-tabs代码 -->
            <div >
              <el-tabs
                v-model="activeIndex"
                type="border-card"
                closable
                v-if="openTab.length"
                 @tab-click='tabClick'
                  @tab-remove='tabRemove'
                >
                <el-tab-pane
                 class="tag-nav-item"
                 :class="isActive(item) ? 'cur' : ''"
                  :key="item.name"
                  v-for="(item) in openTab"
                  :label="item.name"
                  :name="item.route"
                 >
                </el-tab-pane>
              </el-tabs>
            </div>
            <div class="content-wrap">
              <router-view/>
            </div>
        </div>
      </el-col>
     </el-row>
    </el-container>
  </el-container>
</template>

<script>
// @ is an alias to /src
import Slider from '@/components/Slider.vue'
import navTop from '@/components/Header.vue'
export default {
  name: 'Home',
  components: {
    Slider, navTop
  },
  data () {
    return {

    }
  },
  methods: {
    isActive (item) {
      return item.path === this.$route.path
    },
    // tab标签点击时，切换相应的路由
    tabClick (tab) {
      console.log('tab', tab)
      console.log('active', this.activeIndex)
      this.$router.push({path: this.activeIndex})
    },
    // 移除tab标签
    tabRemove (targetName) {
      console.log('tabRemove', targetName)
      // 首页不删
      if (targetName === '/main') {
        return
      }
      this.$store.commit('delete_tabs', targetName)
      if (this.activeIndex === targetName) {
        // 设置当前激活的路由
        if (this.openTab && this.openTab.length >= 1) {
          console.log('=============', this.openTab[this.openTab.length - 1].route)
          this.$store.commit('set_active_index', this.openTab[this.openTab.length - 1].route)
          this.$router.push({path: this.activeIndex})
        } else {
          this.$router.push({path: '/main'})
        }
      }
    }
  },
  mounted () {
    // 刷新时以当前路由做为tab加入tabs
    // 当前路由不是首页时，添加首页以及另一页到store里，并设置激活状态
    // 当当前路由是首页时，添加首页到store，并设置激活状态
    if (this.$route.path !== '/main' && this.$route.path !== '/home') {
      console.log('1')
      this.$store.commit('add_tabs', {route: '/main', name: '首页'})
      this.$store.commit('add_tabs', {route: this.$route.path,
        name: this.$route.name })

      console.log(this.$route.path)
      this.$store.commit('set_active_index', this.$route.path)
    } else {
      console.log(this.$route.path)
      console.log('2')
      this.$store.commit('add_tabs', {route: '/main', name: '首页'})
      this.$store.commit('set_active_index', '/main')
      // this.$router.push('/')
      // this.$store.commit('set_active_index', '/')
    }
  },
  computed: {
    openTab () {
      // console.log(this.$store.state.openTab)
      return this.$store.state.openTab
    },
    activeIndex: {
      get () {
        return this.$store.state.activeIndex
      },
      set (val) {
        this.$store.commit('set_active_index', val)
      }
    }
  },
  watch: {
    '$route' (to, from) {
      // 判断路由是否已经打开
      // 已经打开的 ，将其置为active
      // 未打开的，将其放入队列里
      let flag = false
      for (let item of this.openTab) {
        console.log('item.name', item.name)
        console.log('t0.name', to.name)

        if (item.name === to.name) {
          console.log('to.path', to.path)
          this.$store.commit('set_active_index', to.path)
          flag = true
          break
        }
      }

      if (!flag) {
        console.log('to.path', to.path)
        this.$store.commit('set_active_index', to.path)
        // this.$store.state.activeIndex = '/main'
        if (to.path === '/home' && to.name === undefined) {
          return
        }
        this.$store.commit('add_tabs', {route: to.path, name: to.name})
      }
    }
  }

}
</script>

<style lang='less' scoped>

.home{
  height:100%;
}
.app-wrap{
  padding-top: 20px;
  padding-left: 20px;
}
.content-wrap{
  // border: 1px solid #DCDFF7;
  border-top: none;
}
</style>
<style>
.el-tabs.el-tabs--border-card {
    box-shadow: none;
    border-bottom: none;
}
.el-tabs--border-card>.el-tabs__header .el-tabs__item{
  background-color: rgba(82,186,181,.1);
  color: #52bab5;
  border-radius: 9px;
  margin-left: 10px;
}
.el-tabs--border-card>.el-tabs__header .el-tabs__item.is-active{
  background-color: rgba(82,186,181,.1);
  border-radius: 9px;
  color: #52bab5;
}
.el-tabs--border-card>.el-tabs__header .el-tabs__item:not(.is-disabled):hover{
  color: #fff;
}
.el-tabs--border-card>.el-tabs__header .el-tabs__item:not(.is-active):hover{
  color: #52bab5;
}
.el-tabs--border-card>.el-tabs__header{
  background-color:#fff;
  border-bottom:none;
}
.el-tabs--border-card{
  border:none;
}
.el-tabs--border-card>.el-tabs__header .el-tabs__item.is-active{
   background-color: #52bab5;
   border-radius: 9px;
   color: #fff;
}
 .cur{
   background-color: #52bab5;
   border-radius: 9px;
   color: #fff;
 }
</style>
