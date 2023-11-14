<template>
        <el-menu
        :unique-opened="true"
        :default-active="$route.path"
        class="slider"
        background-color="#F4F4F4"
        text-color="#000"
        active-text-color="#000"
        router
        >
        <el-menu-item index="/produceMain" >
          <i class="el-icon-menu"></i>
            <span slot="title">销售商首页</span>
        </el-menu-item>

        <el-submenu v-for="(item,index) in menuList" :key="index" :index='item.id'>
          <template slot="title">
            <i :class="item.icon"></i>
            <span>{{item.title}}</span>
          </template>
          <el-menu-item-group >
            <el-menu-item v-for="child in item.children" :key="child.id" :index='child.index'>{{child.childtitle}}</el-menu-item>
          </el-menu-item-group>
        </el-submenu>


      </el-menu>
</template>

<script>
export default {
  name: 'ProduceSlider',
  data () {
    return {
      menuList: [
        {
          id: '1',
          title: '销售管理',
          icon: 'el-icon-location',
          children: [
						// {
						// 	index: '/rateprefamily',
						// 	childtitle: '心率血压'
						// }
						// ,
						// {
						// 	index: '/stepfamily',
						// 	childtitle: '睡眠步数'
						// },
						// {
						// 	index: '/positionfamily',
						// 	childtitle: '位置信息'
						// }

            // {
            //   index: '/lifefamily',
            //   childtitle: '生活信息'
            // },
            {
              index: '/tracesale',
              childtitle: '信息管理'
            }

          ]
        }
        ,
        {
          id: '2',
          title: '个人管理',
          icon: 'el-icon-location',
          children: [
            {
              index: '/updatespassword',
              childtitle: '密码修改'
            }
          ]
        }
      ]

    }
  }

}
</script>
<style scoped>
.slider{
  height:100vh;
}
</style>

<style lang="less">
.el-menu{
 .el-submenu{
   .el-menu-item{
     padding-left:92px!important;
   }
 }
}
.el-submenu__title:hover{
   background-color: rgba(82,186,181,.38) !important;
}
.el-menu-item:focus,.el-menu-item:hover{
  background-color: rgba(82,186,181,.38) !important;
}

</style>
