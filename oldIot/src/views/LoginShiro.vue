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
          <span style="font-size: 20px;">Login</span>
        </div>
        <el-form-item
          label="用户名"
          prop="username"
        >
          <el-input v-model="loginForm.username"></el-input>
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
		
    return {
      // 表单数据
      loginForm: {
        username: '',
        password: ''
      },
			user:'',
			familyTouPic:'',
			familyPhone:'',
			oldId:'',
      // 表单验证规则
      rules: {
        password: [
          { required: true, message: '请输入密码', trigger: 'blur' },
          { min: 6, max: 20, message: '密码长度为6-20个字符', trigger: 'blur' }
        ]
      }
    }
  },
  methods: {
    submitForm (formName) {
      console.log(formName)
			var _this = this
	    console.log("进入登入")
	    axios.post('restful/userLogin/loginTwo',
	      _this.loginForm,
	      {
	        headers: {
	          'content-type': 'application/json'
	        },
	        withCredentials: true
	      }).then(function (response) {
					console.log(response)
					_this.$router.push({ path: '/home' })
											_this.user="alice"
											console.log("管理员")
											localStorage.setItem('token', true)
// 					if(response.data.users1!=null){
// 						_this.user=response.data.users1.userName
// 						console.log("管理员")
// 						localStorage.setItem('token', true)
// 						_this.$router.push({ path: '/home' })
// 					}else{
// 						_this.user=response.data.family.familyName
// 						_this.familyTouPic=response.data.family.familyTouPic
// 						_this.familyPhone=response.data.family.familyPhone
// 						_this.oldId=response.data.family.oldId
// 						console.log("家属")
// 						console.log(_this.oldId)
// 						console.log(_this.familyTouPic+'----'+_this.familyPhone+'===='+_this.oldId)
// 						localStorage.setItem('oldIdfamily', _this.oldId)
// 						localStorage.setItem('familyPhone', _this.familyPhone)
// 						localStorage.setItem('familyTouPic', _this.familyTouPic)
// 						localStorage.setItem('token', true)
// 						_this.$router.push({ path: '/familyHome' })
// 					}
// 					localStorage.setItem('user_name', _this.user)
	      
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
