<template>
  <div class="home_main">
			
	<el-container>
		<el-header><span style="color: black;font-size: 30px;">个人资料</span><br /><br /><br /></el-header>
		  <el-container>
			<el-aside style="widows: 110px;height: 300px;">
				<!-- <div style="width: 110px;height: 110px; "> -->
					<img style="width: 100px;height: 100px;border-radius: 50%;" :src="imageUrl" />
				<!-- </div> -->
				<!-- <img style="width: 100px;height: 100px;border-radius: 50%;margin-bottom: 60px;" v-if="imageUrl" :src="imageUrl" class="avatar">
				<i style="width: 100px;height: 100px;border-radius: 50%;margin-bottom: 60px;" v-else class="el-icon-plus avatar-uploader-icon"></i> -->
				<el-upload
				  class="avatar-uploader"
				  action="image/saveImage"
				  :show-file-list="false"
				  :on-success="handleAvatarSuccess"
				  :before-upload="beforeAvatarUpload">
				  <el-button type="primary">修改头像</el-button>
				</el-upload>
			</el-aside>
			<el-main>
				<span style="float: left;color: #999999;font-size: 30px;">Id: {{this.famiyList.familyId}}</span><br /><br /><br /><hr />
				<span style="float: left;color: black;font-size: 20px;">姓名: {{this.famiyList.familyName}}</span>
				<el-button style="float: right;margin-right: 20px;" type="primary" @click="handleeditMessage">修改资料</el-button>
				<el-button style="float: right;margin-right: 20px;" type="primary" @click="handleeditPassword">修改密码</el-button>
				<br /><br /><br />
				<span style="float: left;color: black;font-size: 20px;">性别: {{this.famiyList.familySex}}</span><br /><br /><br />
				<span style="float: left;color: black;font-size: 20px;">地址: {{this.famiyList.familyAddress}}</span><br /><br /><br />
				<span style="float: left;color: black;font-size: 20px;">电话: {{this.familyPhone}}</span><br /><br /><br />
				
				<!-- start 编辑角色弹窗 -->
				<el-dialog
				  title="编辑资料"
				  :visible.sync="messageEditDialogVisible"
				  width="30%">
				  <el-form label-width="80px" :model="messageEditForm">
				    <el-form-item label="家属ID" prop="familyId" style="display: none; ">
				    <el-input v-model="messageEditForm.familyId"></el-input>
				    </el-form-item>
				    <el-form-item label="姓名" prop="familyName" >
				    <el-input v-model="messageEditForm.familyName"></el-input>
				    </el-form-item>
				    <el-form-item label="性别" prop="familySex">
				    <el-input v-model="messageEditForm.familySex"></el-input>
				    </el-form-item>
						<el-form-item label="地址" prop="address">
						<el-input v-model="messageEditForm.familyAddress"></el-input>
						</el-form-item>
						<el-form-item label="电话" prop="phone">
						<el-input v-model="messageEditForm.familyPhone"></el-input>
						</el-form-item>
				  </el-form>
				  <span slot="footer" class="dialog-footer">
				  <el-button @click="messageEditDialogVisible = false">取 消</el-button>
				  <el-button type="primary" @click="editFamily">确 定</el-button>
				</span>
				</el-dialog>
				<!-- end 编辑角色弹窗 -->
				
				<!-- start 编辑角色弹窗 -->
				<el-dialog
				  title="修改密码"
				  :visible.sync="passwordEditDialogVisible"
				  width="30%">
				  <el-form label-width="80px" class="demo-ruleForm" status-icon :rules="rules2" ref="passwordEditForm" :model="passwordEditForm">
				    <el-form-item label="" prop="familyPhone" style="display: none; ">
				    <el-input v-model="passwordEditForm.familyPhone"></el-input>
				    </el-form-item>
				    <el-form-item label="原密码" prop="oldPassword" >
				    <el-input type="password" v-model="passwordEditForm.oldPassword"></el-input>
				    </el-form-item>
				    <el-form-item label="密码" prop="pass">
							<el-input type="password" v-model="passwordEditForm.pass" auto-complete="off"></el-input>
						</el-form-item>
						<el-form-item label="确认密码" prop="checkPass">
							<el-input type="password" v-model="passwordEditForm.checkPass" auto-complete="off"></el-input>
						</el-form-item>
				  </el-form>
				  <span slot="footer" class="dialog-footer">
				  <el-button @click="resetForm('passwordEditForm')">取 消</el-button>
				  <el-button type="primary" @click="editPassword('passwordEditForm')">确 定</el-button>
				</span>
				</el-dialog>
				<!-- end 编辑角色弹窗 -->
				
			</el-main>
			
		  </el-container>	
	</el-container>
  </div>
</template>
<style scoped>
	.avatar-uploader .el-upload {
    border: 1px dashed #d9d9d9;
    border-radius: 6px;
    cursor: pointer;
    position: relative;
    overflow: hidden;
  }
  .avatar-uploader .el-upload:hover {
    border-color: #409EFF;
  }
  .avatar-uploader-icon {
    font-size: 28px;
    color: #8c939d;
    width: 178px;
    height: 178px;
    line-height: 178px;
    text-align: center;
  }
  .avatar {
    width: 178px;
    height: 178px;
    display: block;
  }
</style>
<script>
	
	import axios from 'axios'
	
export default {
	data() {
        
      var validatePass = (rule, value, callback) => {
        if (value === '') {
          callback(new Error('请输入密码'));
        } else {
          if (this.passwordEditForm.checkPass !== '') {
            this.$refs.passwordEditForm.validateField('checkPass');
          }
          callback();
        }
      };
      var validatePass2 = (rule, value, callback) => {
        if (value === '') {
          callback(new Error('请再次输入密码'));
        } else if (value !== this.passwordEditForm.pass) {
          callback(new Error('两次输入密码不一致!'));
        } else {
          callback();
        }
      };
      return {
        imageUrl: require('../assets/img/QQ图片20170101150550.jpg'),
				messageEditDialogVisible:false,
				passwordEditDialogVisible:false,
				messageEditForm: {
				  familyId: '',
				  familyPhone: '',
				  back: ''
				},
				passwordEditForm: {
				  familyPhone: '',
				  pass: '',
					checkpass:'',
					oldPassword: ''
				},
				personName:'',
				familyPhone:'',
				familyTouPic:'',
				famiyList: [],
				rules2: {
				    pass: [
				      { validator: validatePass, trigger: 'blur' }
				    ],
				    checkPass: [
				      { validator: validatePass2, trigger: 'blur' }
				    ]
				  }
				}
			
    },
		created () {
		  this.personName=localStorage.getItem('user_name')
			this.familyPhone=localStorage.getItem('familyPhone')
			this.familyTouPic=localStorage.getItem('familyTouPic')
			this.imageUrl=require('../assets/img/'+this.familyTouPic)
			this.searchFamily()
			
		},
    methods: {
			searchFamily(){
			  var _this = this
			  axios.post('family/selectTwo',
			    {
			      'familyPhone': _this.familyPhone
			    },
			    {
			      headers: {
			        'content-type': 'application/json'
			      },
			      withCredentials: true
			    }).then(function (response) {
			    console.log(response)
			    _this.famiyList = response.data.familyDto2
					console.log('家属信息')
					console.log(_this.famiyList)
			  })
			    .catch(function (error) {
			      console.log(error)
			    })
			},
			editFamily () {
			  var _this = this
			  axios.post('family/updateTwo', _this.messageEditForm,
			    {
			      headers: {
			        'content-type': 'application/json'
			      },
			      withCredentials: true
			    }).then(function (response) {
			    console.log(response)
			    if (response.data.returnCode === '1111') {
			      _this.messageEditDialogVisible = false
			      _this.$message({
			        type: 'success',
			        message: '编辑监护人信息成功'
			      })
			      _this.searchFamily()
			    } else {
			      _this.$message({
			        type: 'error',
			        message: '编辑监护人信息失败'
			      })
			    }
			  })
			    .catch(function (error) {
			      console.log(error)
			    })
			},
      handleAvatarSuccess(res, file) {
    this.imageUrl = URL.createObjectURL(file.raw);
		var _this=this
		var params = {
						  'picName': file.name
		}
		console.log(params)
		axios.post('picshow/add',params,
			{
				headers: {
					'content-type': 'application/json'
				},
				withCredentials: true
			}).then(function (response) {
			console.log(response)
			_this.$router.push('/main')
		})
			.catch(function (error) {
				console.log(error)
			})
		_this.fileListName=[]
      },
			editMessage(){
				
			},
			editPassword(formName){
				this.$refs[formName].validate((valid) => {
          if (valid) {
            alert('submit!');
          } else {
            console.log('error submit!!');
            return false;
          }
        })
				
			},
			handleeditMessage(){
				var _this=this
				_this.messageEditForm.familyId=_this.famiyList.familyId
				_this.messageEditForm.familyName=_this.famiyList.familyName
				_this.messageEditForm.familySex=_this.famiyList.familySex
				_this.messageEditForm.familyAddress=_this.famiyList.familyAddress
				_this.messageEditForm.familyPhone=_this.familyPhone
				this.messageEditDialogVisible = true
				
			},
			handleeditPassword(){
				console.log('编辑密码')
				var _this=this
				_this.messageEditForm.familyPhone=_this.familyPhone
				_this.passwordEditDialogVisible = true
			},
			resetForm(formName){
				this.passwordEditDialogVisible = false
				this.$refs[formName].resetFields();
			},
      beforeAvatarUpload(file) {
        const isJPG = file.type === 'image/jpeg';
        const isLt2M = file.size / 1024 / 1024 < 2;

        if (!isJPG) {
          this.$message.error('上传头像图片只能是 JPG 格式!');
        }
        if (!isLt2M) {
          this.$message.error('上传头像图片大小不能超过 2MB!');
        }
        return isJPG && isLt2M;
      }
    }
  }
</script>
