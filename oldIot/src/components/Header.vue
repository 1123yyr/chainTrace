<template>
    <el-header class="home-header">
      <el-row>
        <el-col :span="3">
          <div class="home_logo">
            中国
          </div>
        </el-col>
        <el-col :span="14">
          <h3>乳制品溯源管理系统</h3>
        </el-col>
				<!-- <el-col :span="3" class="logout-desc">
					<el-badge :value="4" class="item">
					<el-button size="small" @click="open">不合格</el-button>
					</el-badge>
				</el-col> -->
				<el-col :span="4" class="user">
          &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; <span class="welcome">欢迎使用</span>
				  <span class="username">{{login_username}}</span>
				</el-col>
        <el-col :span="3" class="logout-desc">
          <a href="javascript:;" class="logout" @click.prevent="logout">退出</a>
        </el-col>
      </el-row>
    </el-header>
</template>

<script>
export default {
  name: 'navTop',
	data () {
	  return {
	    login_username: ''
	  }
	},
	created () {
	  this.login_username=localStorage.getItem('user_name')
    console.log("header")
    console.log(this.login_username)
    console.log("header")
	},
  methods: {
		open() {
        const h = this.$createElement;

        this.$notify({
          title: '标题名称',
          message: h('i', { style: 'color: teal'}, '这是提示文案这是提示文案这是提示文案这是提示文案这是提示文案这是提示文案这是提示文案这是提示文案')
        });
      },
    // 退出
    logout () {
      // 弹出确认对话框
      this.$confirm('是否确定退出?', '温馨提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      })
        .then(() => {
          // 点击确定按钮
          this.$message({
            type: 'success',
            message: '退出成功'
          })

					this.$store.commit('clear_tabs', '/login')
          // 退出后，要跳转到登录页面
          this.$router.push('/login')
					localStorage.clear();
          // 移除token
          // localStorage.removeItem('token')
        })
        .catch(() => {
          // 点击取消按钮
          this.$message({
            type: 'info',
            message: '取消退出'
          })
        })
    }
  }

}
</script>

<style scoped>
.header{
    background-color: rgb(85, 201, 236);
    height: 60px;
}
.home-header {
  background-color: #52BAB5;
}
.home-header .home_logo{
  font-weight: bold;
  font-size: 30px;
  color: #fff;
  line-height: 60px;
}

.home-header h3 {
  height: 60px;
  line-height: 60px;
  text-align: center;
  font-size: 28px;
  color: #fff;
}

.home-header .logout-desc {
  line-height: 60px;
  font-weight: bold;
  color: #fff;
}

.home-header .logout-desc .logout {
  color: #fff;
  text-decoration: none;
}
.username{
	line-height: 60px;
  font-size: 13px;
  font-weight: bold;
  color: #fff;
}
.welcome{
	line-height: 60px;
  font-size: 16px;
  font-weight: bold;
  color: #fff;
  margin-right: 5px;
}
</style>
