<template>
  <div class="roles">
    <!-- 查询和添加 -->
    <!-- <div class="sys-research">
      <el-row class="row-search">
        <el-col :span="8">
          <el-input
            placeholder="请输入搜索内容"
            v-model="searchText"
            class="input-with-select">
            <el-button
              slot="append"
              icon="el-icon-search"
              @click="searchWatchs"></el-button>
          </el-input>
        </el-col>
      </el-row>
    </div> -->
    <!-- start 添加角色弹窗 -->
    <el-dialog
      title="角色添加"
      :visible.sync="watchDialogVisible"
      @close="closeRoleAddDialog"
      width="30%">
      <el-form label-width="80px" :model="watchForm"  ref="addwatchFormRef">
        <!-- <el-form-item label="老人姓名" prop="name">
          <el-input v-model="watchForm.name"></el-input>
        </el-form-item> -->
        <el-form-item label="手表Id" prop="deviceId">
          <el-input v-model="watchForm.deviceId"></el-input>
        </el-form-item>
		<el-form-item label="血压高值" prop="bpf">
		<el-input v-model="watchForm.bpf"></el-input>
		</el-form-item>
		<el-form-item label="血压低值" prop="bpl">
		<el-input v-model="watchForm.bpl"></el-input>
		</el-form-item>
		<el-form-item label="心率高值" prop="hrf">
		<el-input v-model="watchForm.hrf"></el-input>
		</el-form-item>
		<el-form-item label="心率低值" prop="hrl">
		<el-input v-model="watchForm.hrl"></el-input>
		</el-form-item>
      </el-form>
      <span slot="footer" class="dialog-footer">
      <el-button @click="watchDialogVisible = false">取 消</el-button>
      <el-button type="primary" @click="addWatchs('addwatchFormRef')">确 定</el-button>
    </span>
    </el-dialog>
    <!-- end 添加角色弹窗 -->
    <!-- start 编辑角色弹窗 -->
    <el-dialog
      title="评论回复"
      :visible.sync="watchEditDialogVisible"
      width="30%">
      <el-form label-width="80px" :model="saidEditForm">
        <el-form-item label="" prop="saidId" style="display: none; ">
        <el-input v-model="saidEditForm.saidId"></el-input>
        </el-form-item>
        <el-form-item label="评论" prop="said" >
        <el-input v-model="saidEditForm.said" disabled="disabled"></el-input>
        </el-form-item>
        <el-form-item label="回复" prop="back">
        <el-input v-model="saidEditForm.back"></el-input>
        </el-form-item>
      </el-form>
      <span slot="footer" class="dialog-footer">
      <el-button @click="watchEditDialogVisible = false">取 消</el-button>
      <el-button type="primary" @click="editSaid">确 定</el-button>
    </span>
    </el-dialog>
    <!-- end 编辑角色弹窗 -->
    <!-- 表格 -->
    <el-table :data="saidList.slice((currentPage-1)*pageSize,currentPage*pageSize)"
    style="width: 100%" >
    <!-- 可展开行 -->
      
      <el-table-column prop="touPic" label="" width="100" align="center">
		  <template slot-scope="scope">
		    <img style="width: 60px;height: 60px;border-radius: 50%;" :src="scope.row.touPic" />
		  </template>
	  </el-table-column>
      <el-table-column prop="personName" label="作者" width="100" align="center"></el-table-column>
	  <!-- <el-table-column prop="saidId" style="display: none;" label="作者" width="160" align="center"></el-table-column> -->
      <el-table-column prop="said" label="评论" width="160" align="center"></el-table-column>
			<el-table-column prop="saidTime" label="评论时间" width="180" align="center"></el-table-column>
			<el-table-column prop="back" label="回复" width="160" align="center"></el-table-column>
			<el-table-column prop="backTime" label="回复时间" width="180" align="center"></el-table-column>
			<el-table-column prop="title" label="文章标题" width="160" align="center"></el-table-column>
	  <!-- <el-table-column prop="fee" label="话费余额" width="140" align="center"></el-table-column>
	  <el-table-column prop="power" label="电量" width="140" align="center"></el-table-column> -->
      <el-table-column label="操作" align="center">
        <template slot-scope="scope">
          <el-button v-if="scope.row.back==null" size="mini" plain type="primary" icon="el-icon-edit"
                     @click="handleeditSaid(scope.$index, scope.row)">回复
          </el-button>
          <el-button size="mini" plain type="danger" icon="el-icon-delete" @click="deleteSaid(scope.row.saidId)">删除
          </el-button>
          
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
    <!-- 分配权限对话框 -->
    <el-dialog title="角色授权" :visible.sync="isShowRoleDialog">
      <!--
        Tree 树形控件
          :data="data2" 数据源，数据中通过 label 属性表示树形节点的名称；通过 children 属性来指定该节点的子节点
          show-checkbox 添加该属性后，节点会带有复选框
          :default-expanded-keys="[2, 3]" 设置默认展开的节点key（key对应数据中的id）
          :default-checked-keys="[5]" 设置默认选中的节点key（id）
          node-key="id" 使用数据中的哪个属性来表示每个节点的key值，一般就是唯一的id值
          :props="defaultProps"
          :default-expand-all="true" 是否默认展开所有节点
      -->
      <el-tree
        ref="tree"
        :data="rightsTree"
        :default-expand-all="true"
        node-key="id"
        show-checkbox
        :props="defaultProps"
      >
      </el-tree>

      <div slot="footer" class="dialog-footer">
        <el-button @click="isShowRoleDialog = false">取 消</el-button>
        <el-button type="primary" @click="assignRights">确 定</el-button>
      </div>
    </el-dialog>
  </div>

</template>

<script>
// 引入
import axios from 'axios'

export default {
  data () {
    return {
      searchText: '', // 角色查询内容
      watchDialogVisible: false,
      watchEditDialogVisible: false,
      watchForm: {
        deviceId: '',
				bpf: '',
				bpl: '',
				hrf: '',
				hrl: ''
      },
      saidEditForm: {
        saidId: '',
        said: '',
        back: ''
      },
      popoverRole: true,
      total: 0,
      pageSize: 10,
      currentPage: 1,
      saidList: [{
		  saidId:'1',
		  touPic:require('../assets/img/QQ图片20170101150550.jpg'),
		  personName:'1',
		  said:'小黄',
		  saidTime:'60',
		  back:'120',
		  backTime:'60',
		  title:'120'
	  }],
      // 控制分配权限对话框的展示和隐藏
      isShowRoleDialog: false,

      // 树形控件的数据
      rightsTree: [],

      // Tree树形控件，默认使用数据中的 label 属性作为节点名称，使用 children 属性指定该接待你的子节点
      // 但是，如果我们自己的数据结构与默认的结构不同，可以通过 defaultProps 来修改默认的两个属性
      defaultProps: {
        // children: 'children',
        label: 'authName'
      },
      // 当前被分配权限的角色id
      roleId: -1
    }
  },
  created () {
    this.searchSaid()
  },
  methods: {
    // 查询
    searchSaid () {
      var _this = this
        
        axios.post('restful/said/selectByUser',
          {
            // 'oldName': oldName === '' ? '' : _this.searchText
          },
          {
            headers: {
              'content-type': 'application/json'
            },
            withCredentials: true
          }).then(function (response) {
          var tableData2 = response.data.saidDtos
          _this.total = response.data.saidDtos.length
					for(var i=0;i<tableData2.length;i++){
						console.log(tableData2[i].touPic)
						tableData2[i].touPic = 'http://47.106.37.197:9110/'+tableData2[i].touPic
					}
					_this.saidList=tableData2
      console.log(_this.saidList)
        })
          .catch(function (error) {
            console.log(error)
          })
      
    },
    // 添加角色
    addWatchs () {
      var _this = this
      axios.post('restful/watchInfo/add',
        _this.watchForm,
        {
          headers: {
            'content-type': 'application/json'
          },
          withCredentials: true
        }).then(function (response) {
        console.log(response)
        if (response.data.returnCode === '1111') {
          _this.watchDialogVisible = false
          _this.searchSaid()
          _this.$message({
            type: 'success',
            message: '添加手表成功'
          })
        } else {
          _this.$message({
            type: 'error',
            message: '添加手表失败'
          })
        }
      })
        .catch(function (error) {
          console.log(error)
        })
    },
    // 关闭添加角色对话框
    closeRoleAddDialog () {
      // 重置表单
      this.$refs.addwatchFormRef.resetFields()
    },
    // 编辑
    editSaid () {
      var _this = this
      axios.post('restful/said/update',_this.saidEditForm,
        {
          headers: {
            'content-type': 'application/json'
          },
          withCredentials: true
        }).then(function (response) {
        console.log(response)
        if (response.data.returnCode === '1111') {
          _this.watchEditDialogVisible = false
          _this.$message({
            type: 'success',
            message: '回复评论成功'
          })
          _this.searchSaid()
        } else {
          _this.$message({
            type: 'error',
            message: '回复评论失败'
          })
        }
      })
        .catch(function (error) {
          console.log(error)
        })
    },
    // 删除
    async deleteSaid (saidId) {

      var _this = this
      try {
        await _this.$confirm('此操作将永久删除该文件, 是否继续?', '提示', {
          confirmButtonText: '确定',
          cancelButtonText: '取消',
          type: 'warning'
        })
        axios.post('restful/said/delete', {'saidId': saidId},
          {
            headers: {
              'content-type': 'application/json'
            },
            withCredentials: true
          }).then(function (response) {
          console.log(response)
          if (response.data.returnCode === '1111') {
            _this.$message({
              type: 'success',
              message: '删除信息成功'
            })
          }
          _this.searchSaid()
        })
          .catch(function (error) {
            console.log(error)
          })
      } catch (err) {
        // 取消删除
        this.$message({
          type: 'info',
          message: '已取消删除'
        })
      }
    },
    // 编辑信息
    handleeditSaid (index, row) {
      this.watchEditDialogVisible = true
      this.saidEditForm = Object.assign({}, row) // 解决修改弹出框数据时，表格数据会发生同步改变
      // console.log(row)
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
    // 展示分配权限对话框
    showRoleDialog (curRole) {
      // curRole 表示当前角色的数据，children属性是当前角色拥有的权限
      console.log('showRoleDialog', curRole)
      // 展示对话框
      this.isShowRoleDialog = true
      // 暂存角色id
      this.roleId = curRole.id
    },

    // 给角色分配权限
    async assignRights () {
    }
  }
}

</script>

<style lang="less" scoped>
  .sys-research {
    height: 60px;
    padding: 20px 20px 0;
  }

  .level1 {
    border-bottom: 1px dotted #ccc;
    padding: 10px 0;

    &:last-child {
      border: 0;
    }
  }

  .level2 {
    padding: 5px 0;
  }

  .level3 {
    margin: 3px;
  }

</style>
