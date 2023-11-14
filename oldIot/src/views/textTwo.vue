<template>
	<div>
		<el-form ref="textform" :model="textform" label-width="80px">
		
		<el-form-item label="标题">
			<el-input v-model="textform.title"></el-input>
		  </el-form-item>
		<el-form-item label="内容">
			<el-input type="textarea" v-model="textform.textMain"></el-input>
		  </el-form-item>
		</el-form>
		<div style="margin-left: 35px;">
			<el-upload
			class="upload-demo"
			action="restful/image/saveImage"
			:on-preview="handlePreview"
			:on-remove="handleRemove"
			:on-success="handleAvatarSuccess"
			:before-upload="beforeAvatarUpload"
			multiple
			:limit="10"
			:file-list="fileList2"
			list-type="picture">
			
			<el-button size="small" type="primary">点击上传</el-button>
			<div slot="tip" class="el-upload__tip">只能上传jpg/png文件，且不超过500kb</div>
			</el-upload>
		</div>
		<el-button type="primary" style="float: right;margin-top: 10px;" @click="textformcommit">提交</el-button>
	</div>
</template>

<script>
	
	import axios from 'axios'
	
	export default {
		data() {
			return {
				textform:{
					name:'',
					textMain:''
				},
				fileListName:[],
				// fileList2: [{name: 'food.jpeg', url: 'https://fuss10.elemecdn.com/3/63/4e7f3a15429bfda99bce42a18cdd1jpeg.jpeg?imageMogr2/thumbnail/360x360/format/webp/quality/100'}, {name: 'food2.jpeg', url: 'https://fuss10.elemecdn.com/3/63/4e7f3a15429bfda99bce42a18cdd1jpeg.jpeg?imageMogr2/thumbnail/360x360/format/webp/quality/100'}]
				fileList2: []
			
			};
			
		},
		created(){
			console.log('==============' + 'created' + '===================')
			this.textform.name=this.$route.query.nametwo
			this.textform.textMain=this.$route.query.textMain
			// console.log(this.textform)
			// console.log(this.textform.name+'------'+this.$route.query.textMain)
		},
		mounted () {
// 			console.log('==============' + 'mounted' + '===================')
// 			this.textform.name=this.$route.params.nametwo
// 			this.textform.textMain=this.$route.params.textMain
			// console.log(this.textform)
			// console.log(this.$route.params+'------'+this.$route.params.textMain)
// 			var para = {
// 				userId: this.formInline.userId,
// 				status: this.formInline.status,
// 				ficDeviceId: this.$route.params.ficDeviceNo
// 			}
// 			this.findPersonList(para)
		},
		methods: {
		  handleRemove(file, fileList) {
			console.log(file, fileList);
		  },
		  handlePreview(file) {
			console.log(file);
		  },
		  textformcommit() {
			  var _this=this
			  console.log(_this.fileListName+'2222222222222222222')
			  var params = {
				  'title': _this.textform.title,
				  'textMain': _this.textform.textMain,
				  'fileListName': _this.fileListName
			  }
			  console.log(params)
			  axios.post('restful/text/add',params,
			  	{
			  		headers: {
			  			'content-type': 'application/json'
			  		},
			  		withCredentials: true
			  	}).then(function (response) {
			  	console.log(response)
			  	_this.$router.push('/textOpt')
			  })
			  	.catch(function (error) {
			  		console.log(error)
			  	})
			  _this.fileListName=[]
			  
      },
	  handleAvatarSuccess(res, file) {
        this.imageUrl = URL.createObjectURL(file.raw);
      },
      beforeAvatarUpload(file) {
		  var _this=this
		  _this.fileListName.push(file.name)
		  console.log(_this.fileListName)
		  console.log(file.name+'----------------------')
        const isJPG = file.type === 'image/jpeg';
        const isLt2M = file.size / 1024 / 1024 < 8;

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

<style>

</style>

