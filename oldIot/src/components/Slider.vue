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
        <el-menu-item index="/main" >
          <i class="el-icon-menu"></i>
          <span slot="title">首页</span>
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
  name: 'Slider',
  data () {
    return {
      menuList: [
        // {
        //   id: '1',
        //   title: '健康管理',
        //   icon: 'el-icon-location',
        //   children: [
        //
				// 		{
				// 			index: '/ratepre',
				// 			childtitle: '心率血压'
				// 		},
				// 		// {
				// 		// 	index: '/step',
				// 		// 	childtitle: '睡眠步数'
				// 		// },
				// 		// {
				// 		// 	index: '/position',
				// 		// 	childtitle: '位置信息'
				// 		// },
				// 		{
				// 			index: '/life',
				// 			childtitle: '生活信息'
				// 		}
        //   ]
        // },
        {
          id: '1',
          title: '信息管理',
          icon: 'el-icon-location',
          children: [



						{
							index: '/produce',
							childtitle: '生产商基本信息'
							// childtitle: '人员信息'
						},
            {
              index: '/transport',
              childtitle: '运输商基本信息'
            },
						{
							index: '/sale',
							childtitle: '销售商基本信息'
						}
						// ,
            // {
            //   index: '/life',
            //   childtitle: '老人生活信息'
            // }
          ]
        },
        // {
        //   id: '2',
        //   title: '账单管理',
        //   icon: 'el-icon-location',
        //   children: [
        //     {
        //       index: '/order',
        //       childtitle: '账单详情'
        //     }
        //     // {
        //     // 	index: '/watch',
        //     // 	childtitle: '手表信息'
        //     // },
        //
        //   ]
        // },
		// {
		// id: '3',
		// title: '公告管理',
		// icon: 'el-icon-location',
		// children: [
		// 	// {
		// 	// index: '/textTwo',
		// 	// childtitle: '公告列表'
		// 	// },
		// 	{
		// 	index: '/textOpt',
		// 	childtitle: '公告详情'
		// 	}
    //
		// 	// {
		// 	// index: '/saidOperation',
		// 	// childtitle: '评论管理'
		// 	// },
    //   // {
    //   // index: '/order',
    //   // childtitle: '订单管理'
    //   // }
		// ]
		// },
    //     {
    //   id: '4',
    //     title: '评论管理',
    //     icon: 'el-icon-location',
    //     children: [
    //     // {
    //     //   index: '/textTwo',
    //     //   childtitle: '公告列表'
    //     // },
    //     // {
    //     //   index: '/textOpt',
    //     //   childtitle: '公告详情'
    //     // },
    //
    //     {
    //       index: '/saidOperation',
    //       childtitle: '评论详情'
    //     }
    //     // {
    //     //   index: '/order',
    //     //   childtitle: '订单管理'
    //     // }
    //   ]
    // },
        {
          id: '2',
          title: '溯源管理',
          icon: 'el-icon-location',
          children: [
            {
              index: '/suyuan',
              childtitle: '信息溯源'
            }
            // {
            // 	index: '/watch',
            // 	childtitle: '手表信息'
            // },

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
