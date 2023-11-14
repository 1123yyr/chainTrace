<template>
  <el-row class="login" type="flex" justify="center" align="middle">
    <el-col
      :xs="14"
      :sm="12"
      :md="10"
      :lg="8"
      :xl="6"
    >
      <el-form
        label-position="left"
        :model="loginForm"
        :rules="rules"
        ref="loginForm"
        label-width="80px"
        class="login-form"
      >
        <div style="height: 80px;text-align: left;">
          <span style="font-size: 30px;">乳制品溯源管理系统</span>
        </div>
        <el-form-item
          label="电话"
          prop="phone"
        >
          <el-input v-model="loginForm.phone"></el-input>
        </el-form-item>
        <el-form-item
          label="密码"
          prop="password"
        >
          <el-input
            type="password"
            v-model="loginForm.password"
          ></el-input>
        </el-form-item>

        <el-form-item>
          <el-button
            class="green"
            @click="submitForm"
          >登录</el-button>
          <el-button @click="resetForm('loginForm')">重置</el-button>
        </el-form-item>
      </el-form>
    </el-col>
  </el-row>
</template>

<script>

import axios from 'axios'
export default {

  data () {
		var checkPhone = (rule, value, callback) => {
		  const phoneReg = /^1[3|4|5|7|8][0-9]{9}$/
		  if (!value) {
		    return callback(new Error('电话号码不能为空'))
		  }
		  setTimeout(() => {
		    // Number.isInteger是es6验证数字是否为整数的方法,但是我实际用的时候输入的数字总是识别成字符串
		    // 所以我就在前面加了一个+实现隐式转换

		    if (!Number.isInteger(+value)) {
		      callback(new Error('请输入数字值'))
		    } else {
		      if (phoneReg.test(value)) {
		        callback()
		      } else {
		        callback(new Error('电话号码格式不正确'))
		      }
		    }
		  }, 100)
		}
    return {
      // 表单数据
      loginForm: {
        phone: '',
        password: ''
      },
      paccount:'',
      saccount:'',
      taccount:'',
			user:'',
			familyTouPic:'',
			familyPhone:'',
			oldId:'',
      customer:'',
      customerId:'',
      customerPhone:'',
      // 表单验证规则
      rules: {
        phone: [
				{ required: true, message: '请输入电话', trigger: 'blur' },
			  { validator: checkPhone, trigger: 'blur'}
			],
        password: [
          { required: true, message: '请输入密码', trigger: 'blur' },
          { min: 6, max: 20, message: '密码长度为6-20个字符', trigger: 'blur' }
        ]
      }
    }
  },
  methods: {
	  resetForm(){
		  this.loginForm.phone=''
		  this.loginForm.password=''
	  },
      submitForm (formName) {
        console.log(formName)
        var _this = this

        axios.post('restful/userLogin/login',
          _this.loginForm,
          {
            headers: {
              'content-type': 'application/json'
            },
            withCredentials: true
	      }).then(function (response) {
					console.log(response)
					if(response.data.users1!=null){
						_this.user=response.data.users1.userName
						console.log("管理员")
						localStorage.setItem('token', true)
						_this.$router.push({ path: '/home' })
					}else if(response.data.family!=null) {
            _this.user = response.data.family.familyName
            _this.familyTouPic = response.data.family.familyTouPic
            _this.familyPhone = response.data.family.familyPhone
            _this.oldId = response.data.family.oldId
            console.log("家属")
            console.log(_this.oldId)
            console.log(_this.familyTouPic + '----' + _this.familyPhone + '====' + _this.oldId)
            localStorage.setItem('oldIdfamily', _this.oldId)
            localStorage.setItem('familyPhone', _this.familyPhone)
            localStorage.setItem('familyTouPic', _this.familyTouPic)
            localStorage.setItem('token', true)
            _this.$router.push({path: '/familyHome'})
					}else if(response.data.produce!=null) {
            _this.user = response.data.produce.pname
            _this.paccount = response.data.produce.paccount
            console.log("生产商")
            localStorage.setItem('pname', _this.pname)
            localStorage.setItem('paccount', _this.paccount)
            localStorage.setItem('token', true)
            localStorage.setItem('user_produce', _this.user)
            _this.$router.push({path: '/produceHome'})
          }else if(response.data.transport!=null) {
					  console.log(response.data)
            _this.user = response.data.transport.tname
            _this.taccount = response.data.transport.taccount
            console.log("运输商")
            localStorage.setItem('tname', _this.tname)
            localStorage.setItem('taccount', _this.taccount)
            localStorage.setItem('token', true)
            localStorage.setItem('user_transport', _this.user)
            _this.$router.push({path: '/transportHome'})
          }else if(response.data.sale!=null) {
            _this.user = response.data.sale.sname
            _this.saccount = response.data.sale.saccount
            console.log("运输商")
            localStorage.setItem('sname', _this.sname)
            localStorage.setItem('saccount', _this.saccount)
            localStorage.setItem('token', true)
            localStorage.setItem('user_sale', _this.user)
            _this.$router.push({path: '/saleHome'})
          }
					else{
            _this.user=response.data.customer.customerName
            _this.customerPhone=response.data.customer.customerPhone
            _this.customerId=response.data.customer.customerId
            // _this.oldId=response.data.family.oldId
            console.log("护士===")
            console.log(_this.customerId)
            // console.log(_this.oldId)
            // console.log(_this.familyTouPic+'----'+_this.familyPhone+'===='+_this.oldId)
            localStorage.setItem('customerId', _this.customerId)
            localStorage.setItem('customerPhone', _this.customerPhone)
            // localStorage.setItem('familyTouPic', _this.familyTouPic)
            localStorage.setItem('token', true)
            _this.$router.push({ path: '/customerHome' })
          }
					localStorage.setItem('user_name', _this.user)

	  console.log(response.data.returnCode+'返回')
	    })
	      .catch(function (error) {
	        console.log(error)
	      })

    }
  }
}
</script>

<style scoped>
.login {
  height: 100vh;
  background-color: #52BAB5;
}

.login-form {
  padding: 25px;
  border-radius: 15px;
  background-color: #fff;
}
.green{
  background-color: #52BAB5;
  color: #fff;
}
</style>
