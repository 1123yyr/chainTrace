<template>
  <div class="roles">
    <!-- 查询和添加 -->
    <div class="sys-research">
      <el-row class="row-search">
        <el-col :span="8">
          <!-- 查询 -->
          <el-input
            placeholder="请输入搜索内容"
            v-model="searchText"
            class="input-with-select">
            <el-button
              slot="append"
              icon="el-icon-search"
              @click="searchRoles"></el-button>
          </el-input>
        </el-col>
        <el-col :span="4">
          <el-button plain @click="watchDialogVisible = true">添加手表</el-button>
        </el-col>
      </el-row>
    </div>
    <!-- start 添加角色弹窗 -->
    <el-dialog
      title="角色添加"
      :visible.sync="watchDialogVisible"
      @close="closeRoleAddDialog"
      width="30%">
      <el-form label-width="80px" :model="watchForm"  ref="addwatchFormRef">
        <el-form-item label="老人姓名" prop="name">
          <el-input v-model="watchForm.name"></el-input>
        </el-form-item>
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
      <el-button type="primary" @click="addRoles('addwatchFormRef')">确 定</el-button>
    </span>
    </el-dialog>
    <!-- end 添加角色弹窗 -->
    <!-- start 编辑角色弹窗 -->
    <el-dialog
      title="角色编辑"
      :visible.sync="watchEditDialogVisible"
      width="30%">
      <el-form label-width="80px" :model="watchEditForm">
        <el-form-item label="老人姓名" prop="name">
        <el-input v-model="watchEditForm.name"></el-input>
        </el-form-item>
        <el-form-item label="手表Id" prop="deviceId">
        <el-input v-model="watchEditForm.deviceId"></el-input>
        </el-form-item>
        <el-form-item label="血压高值" prop="bpf">
        <el-input v-model="watchEditForm.bpf"></el-input>
        </el-form-item>
        <el-form-item label="血压低值" prop="bpl">
        <el-input v-model="watchEditForm.bpl"></el-input>
        </el-form-item>
        <el-form-item label="心率高值" prop="hrf">
        <el-input v-model="watchEditForm.hrf"></el-input>
        </el-form-item>
        <el-form-item label="心率低值" prop="hrl">
        <el-input v-model="watchEditForm.hrl"></el-input>
        </el-form-item>
      </el-form>
      <span slot="footer" class="dialog-footer">
      <el-button @click="watchEditDialogVisible = false">取 消</el-button>
      <el-button type="primary" @click="editRole">确 定</el-button>
    </span>
    </el-dialog>
    <!-- end 编辑角色弹窗 -->
    <!-- 表格 -->
    <el-table :data="rolesList.slice((currentPage-1)*pageSize,currentPage*pageSize)"
    style="width: 100%" border>
    <!-- 可展开行 -->
      
      <el-table-column prop="deviceId" label="ID" width="100" align="center"></el-table-column>
      <el-table-column prop="name" label="佩戴老人" width="160" align="center"></el-table-column>
      <el-table-column prop="bpf" label="血压高值" width="160" align="center"></el-table-column>
	  <el-table-column prop="bpl" label="血压低值" width="160" align="center"></el-table-column>
	  <el-table-column prop="hrf" label="心率高值" width="160" align="center"></el-table-column>
	  <el-table-column prop="fee" label="话费余额" width="160" align="center"></el-table-column>
	  <el-table-column prop="power" label="电量" width="160" align="center"></el-table-column>
      <el-table-column label="操作" align="center">
        <template slot-scope="scope">
          <el-button size="mini" plain type="primary" icon="el-icon-edit"
                     @click="handleEditwatch(scope.$index, scope.row)">编辑
          </el-button>
          <el-button size="mini" plain type="danger" icon="el-icon-delete" @click="deleteRole(scope.row.roleName)">删除
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
        name: '',
        deviceId: '',
		bpf: '',
		bpl: '',
		hrf: '',
		hrl: ''
      },
      watchEditForm: {
        name: '',
        deviceId: '',
        bpf: '',
        bpl: '',
        hrf: '',
        hrl: ''
      },
      popoverRole: true,
      total: 0,
      pageSize: 10,
      currentPage: 1,
      rolesList: [{
		  deviceId:'1',
		  name:'小黄',
		  bpf:'60',
		  bpl:'120',
		  hrf:'60',
		  hrl:'120',
		  fee:'100',
		  power:'96'
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
    this.searchRoles()
  },
  methods: {
    // 查询
    searchRoles () {
      console.log('查询角色')
      console.log('search')
      
    },
    // 添加角色
    addRoles () {
      var _this = this
      axios.post('restful/roles/save',
        {
          'roleName': _this.roleForm.roleName
        },
        {
          headers: {
            'content-type': 'application/json'
          },
          withCredentials: true
        }).then(function (response) {
        console.log(response)
        if (response.data.returnCode === '1111') {
          _this.watchDialogVisible = false
          _this.searchRoles()
          _this.$message({
            type: 'success',
            message: '添加角色成功'
          })
        } else {
          _this.$message({
            type: 'error',
            message: '添加角色失败'
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
      this.$refs.addRoleFormRef.resetFields()
    },
    // 编辑
    editRole () {
      var _this = this
      axios.post('restful/roles/update', _this.watchEditForm,
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
            message: '编辑角色信息成功'
          })
          _this.searchRoles()
        } else {
          _this.$message({
            type: 'error',
            message: '编辑角色信息失败'
          })
        }
      })
        .catch(function (error) {
          console.log(error)
        })
    },
    // 删除
    async deleteRole (roleName) {
      console.log(roleName)

      var _this = this
      try {
        await _this.$confirm('此操作将永久删除该文件, 是否继续?', '提示', {
          confirmButtonText: '确定',
          cancelButtonText: '取消',
          type: 'warning'
        })
        console.log(roleName)
        axios.post('restful/roles/delete', {'roleName': roleName},
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
          _this.searchRoles()
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
    handleEditwatch (index, row) {
      this.watchEditDialogVisible = true
      this.watchEditForm = Object.assign({}, row) // 解决修改弹出框数据时，表格数据会发生同步改变
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

