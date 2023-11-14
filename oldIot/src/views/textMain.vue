<template>
	<div>
		<div style="font-size: 30px;font-weight: bold;text-align: center;">{{title}}</div>
		<p style="text-align: center;">{{time}}</p>
		<p>{{textMain}}</p>
		<div style="width: 550px;height: 600px;margin-left: 400px;">
			<el-carousel indicator-position="outside" >
				<el-carousel-item v-for="(img,index) in imgList" :key="index">
				  <img v-bind:src="img.url">
				</el-carousel-item>
			</el-carousel>
		</div>
		<div>
			<el-row>
				<div style="width: 40px;height: 40px; position: absolute;">
					<img style="width: 40px;height: 40px;border-radius: 50%;position: relative;top: 6px;left: 21px;" :src="imgUrl" />
				</div>
				<div>
					<el-form ref="formSaid" :model="formSaid" label-width="80px">
					<el-form-item label="">
						<el-input type="textarea" v-model="formSaid.said" placeholder="留言板"></el-input>
					  </el-form-item>
					  <el-form-item>
						<el-button type="primary" @click="onSubmit" style="float: right;">发表</el-button>
					  </el-form-item>
					  </el-form>
				</div>
			</el-row>
		</div>
		
		<div>
					<el-table
			:data="tableData"
			style="width: 100%">
			<!-- <el-table-column
			label=" "
				type="index">
			</el-table-column> -->
			<el-table-column
				label=" "
				width="50"
				prop="touPic">
				<template slot-scope="scope">
					<div style="width: 40px;height: 40px; ">
						<img style="width: 40px;height: 40px;border-radius: 50%;" :src="scope.row.touPic" />
					</div>
				          <!-- <span v-if="scope.row.preState==1" style="color:red">异常</span>
				          <span v-else style="color: #37B328">正常</span> -->
				</template>
			</el-table-column>
			<el-table-column
				label=""
				width="100"
				prop="personName">
			</el-table-column>
			<el-table-column
				label=""
				width="80"
				prop="saidH">
				<span style="color: #C0C0C0;float: right;" >评论:</span>
			</el-table-column>
			<el-table-column
				label=""
				width="180"
				prop="said">
			</el-table-column>
			<el-table-column
				label=""
				width="80"
				prop="backH">
				<span style="color: #C0C0C0;float: right;" >回复:</span>
			</el-table-column>
			<el-table-column
				label=""
				width="180"
				prop="back">
			</el-table-column>
			
			<el-table-column label=""
				width="400"
				prop="good">
				<template slot-scope="scope" >
					<!-- <span style="float: right;">赞</span> -->
					<span v-if="scope.row.good==1" style="color:red;float: right;" @click="getview(scope.row)">赞</span>
					<span v-else style="color: #37B328;float: right;" @click="getview(scope.row)">赞</span>
				</template>
			</el-table-column>
			<el-table-column
				label=""
				width="180"
				prop="niceCount">
			</el-table-column>
			</el-table>
		</div>
	</div>
</template>

<script>
	
	import axios from 'axios'
	
	export default {
		created(){
			this.picId=this.$route.query.picId
			this.textId=this.$route.query.textId
			console.log(this.picId+'55555 '+this.textId)
			this.familyPhone=localStorage.getItem('familyPhone')
			this.familyTouPic=localStorage.getItem('familyTouPic')
			this.userName=localStorage.getItem('user_name')
			this.imgUrl=require('../assets/img/'+this.familyTouPic)
			console.log('phone')
			console.log(this.familyPhone)
			this.searchDetail()
			this.searchSaid()
		},
		data() {
			return {
				title: '',
				textId: '',
				picId: '',
				time: '',
				textMain: '',
				formSaid: {
					said: ''
				},
				imgUrl: '',
				tableData: [{
				said: 'hello',
				back: '你好',
				personName:'xiaohang',
				niceCount: '10'
				}, {
				said: 'hello',
				back: '你好啊',
				personName:'xiaohang',
				niceCount: '20'
				}],
				imgList:[
// 				{url:require('../assets/img/1.jpg')},
// 				{url:require('../assets/img/2.jpg')},
// 				{url:require('../assets/img/image.jpg')}
			  ],
			  userName:'',
			  good:'1',
			  t: true,
			  familyPhone: ''
			};
		},
		methods: {
			searchDetail(){
				console.log(this.picId+'55555 '+this.textId)
				var _this=this
				var params = {
					'textId': this.textId
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
				_this.title=response.data.text.title
				_this.time=response.data.text.time
				_this.textMain=response.data.text.textMain
				var pics=response.data.pics
				_this.imgList=response.data.pics
				for(var i=0;i<_this.imgList.length;i++){
					console.log(_this.imgList[i].url)
					_this.imgList[i].url = 'http://47.106.37.197:9110/'+pics[i].picName
				}
				
				
				})
				.catch(function (error) {
					console.log(error)
				})
			},
			searchSaid(){
				var _this=this
				var params = {
					'textId': this.textId,
					'familyPhone':this.familyPhone
				}
				console.log(params)
				axios.post('restful/said/select',params,
				{
					headers: {
						'content-type': 'application/json'
					},
					withCredentials: true
				}).then(function (response) {
				console.log(response)
				console.log('====================================')
				var tableData2 = response.data.said2s
				for(var i=0;i<tableData2.length;i++){
					console.log(tableData2[i].touPic)
					tableData2[i].touPic = 'http://47.106.37.197:9110/'+tableData2[i].touPic
				}
				_this.tableData=tableData2
				_this.total = response.data.said2s.length
				
				})
				.catch(function (error) {
					console.log(error)
				})
			},
			getview(rowed){
				console.log('赞加一')
				
				var _this=this
				var params = {
					'saidId': rowed.saidId,
					'phone':this.familyPhone
				}
				console.log(params)
				axios.post('restful/said/goodCount',params,
				{
					headers: {
						'content-type': 'application/json'
					},
					withCredentials: true
				}).then(function (response) {
				console.log(response)
				console.log('赞的处理2')
				_this.searchSaid()
				})
				.catch(function (error) {
					console.log(error)
				})
				
			},
			onSubmit(){
				var _this=this
				var params = {
					'phone':_this.familyPhone,
					'said':_this.formSaid.said,
					'textId':_this.textId,
					'personName':_this.userName,
					'touPic':_this.familyTouPic
				}
				console.log(params)
				axios.post('restful/said/insert',params,
				{
					headers: {
						'content-type': 'application/json'
					},
					withCredentials: true
				}).then(function (response) {
				console.log(response)
				console.log('添加评论2')
				_this.searchSaid()
				_this.formSaid.said=''
				})
				.catch(function (error) {
					console.log(error)
				})
			}
			
		}
	}
</script>

<style>

</style>
