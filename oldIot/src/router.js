import Vue from 'vue'
import Router from 'vue-router'
import Login from './views/Login.vue'
import Home from './views/Home.vue'
import familyHome from './views/familyHome.vue'
import main from './views/main.vue'
import familyMain from './views/familyMain.vue'
import lifefamily from "./views/lifefamily";
import orderfamily from "./views/orderfamily";
// import roles from './views/roles.vue'
import ratepre from './views/ratepre.vue'
import rateprefamily from './views/rateprefamily.vue'
// import rights from './views/rights.vue'
import annex from './views/annex.vue'
import text from './views/text.vue'
import textTwo from './views/textTwo.vue'
import textMain from './views/textMain.vue'
import textMainUser from './views/textMainUser.vue'
import step from './views/step.vue'
import stepfamily from './views/stepfamily.vue'
// import warning from './views/warning.vue'
import position from './views/position.vue'
import positionfamily from './views/positionfamily.vue'
import life from './views/life.vue'
import watch from './views/watch.vue'
import person from './views/person.vue'
import customer from './views/customer.vue'
import textOpt from './views/textOpt.vue'
import family from './views/family.vue'
import me from './views/me.vue'
import saidOperation from './views/saidOperation.vue'
import order from "./views/order";
import customerHome from "./views/customerHome";
import customerMain from "./views/customerMain";
import ordercustomer from "./views/ordercustomer";
import suyuan from "./views/suyuan";
import produceHome from "./views/produceHome";
import produceMain from "./views/produceMain";
import traceproduce from "./views/traceproduce";
import transportHome from "./views/transportHome";
import transportMain from "./views/transportMain";
import tracetransport from "./views/tracetransport";
import saleHome from "./views/saleHome";
import saleMain from "./views/saleMain";
import tracesale from "./views/tracesale";
import produce from "./views/produce";
import transport from "./views/transport";
import sale from "./views/sale";
import updateppassword from "./views/updateppassword";
import updatespassword from "./views/updatespassword";
import updatetpassword from "./views/updatetpassword";


Vue.use(Router)

const router = new Router({
  routes: [
	  {path: '/',
	    redirect: '/login'
	  },
    {path: '/login',
      component: Login
    },
    {path: '/customerHome',
      component: customerHome,
      redirect: '/customerMain',
      children: [{
        path: '/customerMain',
        name: '护士首页',
        component: customerMain
      },
        {
          path: '/ordercustomer',
          name: '生活账单',
          component: ordercustomer
        }]
    },
    {path: '/produceHome',
      component: produceHome,
      redirect: '/produceMain',
      children: [{
        path: '/produceMain',
        name: '生产商首页',
        component: produceMain
      },
        {
          path: '/traceproduce',
          name: '信息管控',
          component: traceproduce
        },
        {
          path: '/updateppassword',
          name: '更改密码',
          component: updateppassword
        }]
    },
    {path: '/transportHome',
      component: transportHome,
      redirect: '/transportMain',
      children: [{
        path: '/transportMain',
        name: '运输商首页',
        component: transportMain
      },
        {
          path: '/tracetransport',
          name: '信息管控',
          component: tracetransport
        },
        {
          path: '/updatetpassword',
          name: '更改密码',
          component: updatetpassword
        }
        ]
    },
    {path: '/saleHome',
      component: saleHome,
      redirect: '/saleMain',
      children: [{
        path: '/saleMain',
        name: '生产商首页',
        component: saleMain
      },
        {
          path: '/tracesale',
          name: '信息管控',
          component: tracesale
        },
        {
          path: '/updatespassword',
          name: '更改密码',
          component: updatespassword
        }]
    },
	{path: '/familyHome',
	  component: familyHome,
	  redirect: '/familyMain',
	  children: [{
				  path: '/familyMain',
				  name: '家属首页',
				  component: familyMain
				},
				{
					path: '/rateprefamily',
					name: '心率血压',
					component: rateprefamily
				},
      {
        path: '/lifefamily',
        name: '生活信息',
        component: lifefamily
      },
        {
          path: '/orderfamily',
            name: '生活账单',
          component: orderfamily
        },
				{
					path: '/me',
					name: '个人中心',
					component: me
				},
				{
					path: '/textMain',
					name: '公告内容',
					component: textMain
				}]
	},
    { path: '/home',
      component: Home,
      redirect: '/main',
      children: [
				{
				  path: '/main',
				  name: '首页',
				  component: main
				},
				{
					path: '/ratepre',
					name: '心率血压',
					component: ratepre
				},
				{
					path: '/step',
					name: '睡眠步数',
					component: step
				},
				{
					path: '/position',
					name: '位置信息',
					component: position
				},
				{
					path: '/life',
					name: '生活信息',
					component: life
				},
				{
					path: '/watch',
					name: '手表信息',
					component: watch
				},
				{
					path: '/person',
					name: '人员信息',
					component: person
				},
        {
          path: '/produce',
          name: '生产商信息',
          component: produce
        },
        {
          path: '/transport',
          name: '运输商信息',
          component: transport
        },
        {
          path: '/sale',
          name: '销售商信息',
          component: sale
        },
        {
          path: '/suyuan',
          name: '账单溯源',
          component: suyuan
        },
				{
					path: '/customer',
					name: '监护人信息',
					component: customer
				},
				{
					path: '/text',
					name: '公告动态编辑',
					component: text
				},
				{
					path: '/textTwo',
					name: '公告动态',
					component: textTwo
				},
				{
					path: '/textMainUser',
					name: '公告内容',
					component: textMainUser
				},
				{
					path: '/textOpt',
					name: '公告管理',
					component: textOpt
				},
				{
				  path: '/annex',
				  name: '附件管理',
				  component: annex
				},
				{
				  path: '/family',
				  name: '家属管理',
				  component: family
				},
				{
				  path: '/saidOperation',
				  name: '评论管理',
				  component: saidOperation
				},
        {
          path: '/order',
          name: '订单管理',
          component: order
        }
      ]
    }
  ]
})

router.beforeEach((to, from, next) => {
	console.log('进入')
  if (to.path === '/login') {
    // 是登录页面，直接进入到 要访问的登录页面
    return next()
  }

  // 不是登录页面
  const token = localStorage.getItem('token')
  console.log("token",token)
  if (token) {
	  console.log('登陆过')
    // 登录过：
    next()
  } else {
	  console.log('未登录过')
    // 没有登录过
    next('/login')
  }
})

export default router
