<template>
  <div class="home_main">
			
			<el-container>
				<el-row type="flex" >
					<span style="margin-left: 30px;font-weight: bold;padding-top: 8px;">公告动态</span>
				  <el-button type="primary" @click="addtext()" style="width: 100px;height: 40px;margin-left: 50px;margin-right: 60px;">添加公告</el-button>
				<el-col :span="8">
					<!-- 查询 -->
					<el-input
						placeholder="请输入搜索内容"
						v-model="searchText"
						class="input-with-select">
						<el-button
							slot="append"
							icon="el-icon-search"
							@click="searchOlds"></el-button>
					</el-input>
				</el-col>
				
				</el-row>
				
			  <el-header style="height: 400px;">
				  <el-table
			:data="tableData.slice((currentPage-1)*pageSize,currentPage*pageSize)"
			@row-dblclick="getDetail"
			style="width: 100%">
			<el-table-column
			label=" "
			  type="index">
			</el-table-column>
			<el-table-column
			  label=" "
			  width="180"
			  prop="title">
			</el-table-column>
			<el-table-column
			  label=""
			  width="180"
			  prop="time">
			</el-table-column>
			
			<el-table-column label="">
			  <template slot-scope="scope">
				<el-button
				  size="mini"
				  @click="handleEdit(scope.$index, scope.row)">编辑</el-button>
				<el-button
				  size="mini"
				  type="danger"
				  @click="handleDelete(scope.$index, scope.row)">删除</el-button>
			  </template>
			</el-table-column>
		  </el-table>
		  <!-- start 分页 -->
		  <div style="margin-top: 10px;text-align: center">
		    <el-pagination
		      @size-change="handleSizeChange"
		      @current-change="changePage"
		      :current-page.sync="currentPage"
		      :page-size="pageSize"
		      layout="total, sizes, prev, pager, next, jumper"
		      :total="total">
		    </el-pagination>
		  </div>
		  <!-- end 分页 -->
	  </el-header>
	  
	  <el-main>
		  <span style="color: #C0C0C0;font-size: 20px;text-align: left;display: flex;">上传图片到公告展示栏</span><hr />
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
		  <el-button type="primary" style="float: right;margin-top: 10px;" @click="picShow">提交</el-button>
	  </el-main>
	</el-container>
  </div>
</template>
<style scoped>
	.time {
    font-size: 13px;
    color: #999;
  }
  
  .bottom {
    margin-top: 13px;
    line-height: 12px;
  }

  .button {
    padding: 0;
    float: right;
  }

  .image {
    width: 100%;
    display: block;
  }

  .clearfix:before,
  .clearfix:after {
      display: table;
      content: "";
  }
  
  .clearfix:after {
      clear: both
  }
</style>
<script>
	
	import axios from 'axios'
	
export default {
	created () {
		// let myChart = this.$echarts.init(document.getElementById('myChart'))
		console.log('---')
		this.searchOlds()
		console.log('---')
	// this.drawLine()
	},
	updated() {
		// this.drawLine()
		
		// this.getAttendanceData();
	},
	mounted(){
		// this.drawLine()
		
	},
	methods: {
      handleEdit(index, row) {
				
        console.log(index, row);
				 // this.$router.push({path: '/text'})
				var title = row.title
				var textMain = row.textMain
				var textId = row.textId
				var picId = row.picId
				this.$router.push({
					path: '/text',
					query: {
						title: title,
						textMain: textMain,
						textId: textId,
						picId: picId
					}
				})
      },
	  picShow(){
		  var _this=this
		  var params = {
		   'fileListName': _this.fileListName
		  }
		  console.log(params)
		  axios.post('restful/picshow/add',params,
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
	  handleSizeChange (val) {
	    console.log(`每页 ${val} 条`)
	    this.pageSize = val
	  },
	  handleCurrentChange (val) {
	    console.log(`当前页: ${val}`)
	    this.currentPage = val
	  },
	  changePage (val) {
	    console.log(`当前页: ${val}`)
	    this.currentPage = val
	  },
	  handleRemove(file, fileList) {
	  			console.log(file, fileList);
	  },
	  handlePreview(file) {
	  			console.log(file);
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
	  },
	  getDetail(row, column, event){
	  	console.log(row, column, event)
	  	var textId = row.textId
	  	var picId = row.picId
	  	console.log('-------------------------------')
	  	this.$router.push({
	  	path: '/textMainUser',
	  	query: {
	  		textId: textId,
	  		picId: picId
	  	}
	  	})
	  },
      handleDelete(index, row) {
        console.log(index, row);
		var _this=this
		
		var params = {
			'textId': row.textId,
			'picId': row.picId
		}
		console.log(params)
		this.$router.push('/textOpt')
		axios.post('restful/text/delete',params,
		{
			headers: {
				'content-type': 'application/json'
			},
			withCredentials: true
		}).then(function (response) {
		console.log(response)
		_this.tableData=response.data.texts
		this.$router.push('/textOpt')
		})
		.catch(function (error) {
			console.log(error)
		})
      },
			addtext(){
				this.$router.push('/textTwo')
			},
			searchOlds(){
				var _this=this
				
				var params = {
					'title': _this.searchText
				}
				console.log(params)
				axios.post('restful/text/select',params,
				{
					headers: {
						'content-type': 'application/json'
					},
					withCredentials: true
				}).then(function (response) {
				console.log(response)
				_this.tableData=response.data.texts
				})
				.catch(function (error) {
					console.log(error)
				})
			}
			
			
    },
  data() {
    return {
	  tableData: [{
          time: '2016-05-02',
          title: '王小虎',
          textmain: '上海市普陀区金沙江路 1518 弄'
        }, {
          time: '2016-05-04',
          title: '王小高',
          textmain: '上海市普陀区金沙江路 1517 弄'
        }],
		searchText: '', // 查询内容
		fileListName:[],
		// fileList2: [{name: 'food.jpeg', url: 'https://fuss10.elemecdn.com/3/63/4e7f3a15429bfda99bce42a18cdd1jpeg.jpeg?imageMogr2/thumbnail/360x360/format/webp/quality/100'}, {name: 'food2.jpeg', url: 'https://fuss10.elemecdn.com/3/63/4e7f3a15429bfda99bce42a18cdd1jpeg.jpeg?imageMogr2/thumbnail/360x360/format/webp/quality/100'}]
		fileList2: [],
		total: 0,
		pageSize: 10,
		currentPage: 1
    };
  }
}
</script>