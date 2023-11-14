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
			multiple
			:limit="10"
			:before-upload="beforeAvatarUpload"
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
					title:'',
					textMain:''
				},
				textId: '',
				fileListName: [],
				// fileList2: [{name: 'kai.jpg', url: 'http://127.0.0.1:9999/image/download?name=kai.jpg'}, {name: 'food2.jpeg', url: 'https://fuss10.elemecdn.com/3/63/4e7f3a15429bfda99bce42a18cdd1jpeg.jpeg?imageMogr2/thumbnail/360x360/format/webp/quality/100'}]
				fileList2: []
			};

		},
		created(){
			console.log('==============' + 'created' + '===================')
			this.textform.title=this.$route.query.title
			this.textform.textMain=this.$route.query.textMain
			this.textId=this.$route.query.textId
			this.picId=this.$route.query.picId
			this.searchPic()
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
			  console.log(file.name+'66666666666666666666')
			  var _this=this
			  console.log(this.fileListName)
			  for(var i=0;i<_this.fileListName.length;i++){
				  if(_this.fileListName[i]==file.name){
					  _this.fileListName.splice(i,1)
				  }
			  }
			  console.log(this.fileListName)
			console.log(file, fileList);
		  },
		  handlePreview(file) {
			console.log(file);
		  },
		  onSubmit() {
        console.log('submit!');
      },
	  	textformcommit() {
	  		var _this=this

	  		var params = {
				'textId': _this.textId,
				'picId': _this.picId,
	  			'title': _this.textform.title,
	  			'textMain': _this.textform.textMain,
	  			'fileListName': _this.fileListName
	  		}
	  		console.log(params)
	  		axios.post('restful/text/update',params,
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
	  		console.log(_this.fileListName)
	  console.log('submit!');
	  },
	  searchPic(){
		  var _this=this
		  var params = {
		  	'textId': _this.textId
		  }
		  console.log(params)
		  axios.post('restful/text/selectDetail',params,
		  {
		  	headers: {
		  		'content-type': 'application/json'
		  	},
		  	withCredentials: true
		  }).then(function (response) {
		  console.log(response)
		  var res = response.data.pics;
		  for(var i=0;i<res.length;i++){
			  _this.fileListName.push(res[i].picName);
			  res[i].name = res[i].picName;
			  res[i].url = 'http://127.0.0.1:9999/image/download?name='+res[i].picName;
			  // console.log(res[i])
		  }
		  _this.fileList2=res
		  console.log(_this.fileListName+'8888888888')
		  })
		  .catch(function (error) {
		  	console.log(error)
		  })
	  },
	  handleAvatarSuccess(res, file) {
		  // var _this=this
        this.imageUrl = URL.createObjectURL(file.raw);
      },
      beforeAvatarUpload(file) {
		  console.log(file)
		  // var _this=this
		  this.fileListName.push(file.name)
		  console.log(this.fileListName+'999999999')
		  // console.log(_this.fileListName)
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
