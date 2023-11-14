<template>
  <div class="home_main">
			
			<el-container>
				<el-row type="flex" >
					<span style="margin-left: 30px;font-weight: bold;padding-top: 8px;margin-right: 50px;">公告动态</span>
					
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
			  	:data="tableData"
				@row-click="getDetail"
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
			  		
			  	</el-table-column>
			  	</el-table>
			  </el-header>
	  <el-main>Main</el-main>
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
	methods: {
      handleEdit(index, row) {
				
        console.log(index, row);
				 // this.$router.push({path: '/text'})
				var nametwo = row.title
				var textMain = row.textmain
				this.$router.push({
					path: '/text',
					query: {
						nametwo: nametwo,
						textMain: textMain
					}
				})
      },
	  getDetail(row, column, event){
		  console.log(row, column, event)
		  var textId = row.textId
		  var picId = row.picId
		  this.$router.push({
		  	path: '/textMain',
		  	query: {
		  		textId: textId,
		  		picId: picId
		  	}
		  })
	  },
      handleDelete(index, row) {
        console.log(index, row);
      },
			addtext(){
				this.$router.push('/text')
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
		searchText: '' // 查询内容
    };
  }
}
</script>