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
          <el-button plain @click="roleDialogVisible = true">添加角色</el-button>
        </el-col>
      </el-row>
    </div>
    <!-- start 添加角色弹窗 -->
    <el-dialog
      title="角色添加"
      :visible.sync="roleDialogVisible"
      @close="closeRoleAddDialog"
      width="30%">
      <el-form label-width="80px" :model="roleForm"  ref="addRoleFormRef">
        <el-form-item label="角色名称" prop="roleName">
          <el-input v-model="roleForm.roleName"></el-input>
        </el-form-item>
        <el-form-item label="角色描述" prop="roleDesc">
          <el-input v-model="roleForm.roleDesc"></el-input>
        </el-form-item>
      </el-form>
      <span slot="footer" class="dialog-footer">
      <el-button @click="roleDialogVisible = false">取 消</el-button>
      <el-button type="primary" @click="addRoles('addRoleFormRef')">确 定</el-button>
    </span>
    </el-dialog>
    <!-- end 添加角色弹窗 -->
    <!-- start 编辑角色弹窗 -->
    <el-dialog
      title="角色编辑"
      :visible.sync="roleEditDialogVisible"
      width="30%">
      <el-form label-width="80px" :model="roleEditForm">
        <el-form-item label="角色名称" prop="roleName">
          <el-input v-model="roleEditForm.roleName"></el-input>
        </el-form-item>
        <el-form-item label="角色描述" prop="roleDesc">
          <el-input v-model="roleEditForm.roleDesc"></el-input>
        </el-form-item>
      </el-form>
      <span slot="footer" class="dialog-footer">
      <el-button @click="roleEditDialogVisible = false">取 消</el-button>
      <el-button type="primary" @click="editRole">确 定</el-button>
    </span>
    </el-dialog>
    <!-- end 编辑角色弹窗 -->
    <!-- 表格 -->
    <el-table :data="rolesList.slice((currentPage-1)*pageSize,currentPage*pageSize)"
    style="width: 100%" border>
    <!-- 可展开行 -->
      <el-table-column type="expand">
        <template slot-scope="scope">
          <!-- 1 渲染一级权限 -->
          <el-row
            v-for="level1 in scope.row.children"
            :key="level1.id"
            class="level1">
            <!-- 展示一级权限名称： -->
            <el-col :span="4">
              <el-tag closable>{{ level1.authName }}</el-tag>
              <i class="el-icon-arrow-right"></i>
            </el-col>
            <!-- 展示 二级和三级权限： -->
            <el-col :span="20">
              <!-- 2 渲染二级权限 -->
              <el-row
                v-for="level2 in level1.children"
                :key="level2.id"
                class="level2">
                <!-- 展示二级权限名称： -->
                <el-col :span="4">
                  <el-tag type="success" closable>{{ level2.authName }}</el-tag>
                  <i class="el-icon-arrow-right"></i>
                </el-col>
                <!-- 展示三级权限名称： -->
                <el-col :span="20">
                  <!-- 3 渲染三级权限 -->
                  <el-tag
                    class="level3"
                    v-for="level3 in level2.children"
                    :key="level3.id"
                    type="warning"
                    closable
                  >{{ level3.authName }}
                  </el-tag>
                </el-col>
              </el-row>
            </el-col>
          </el-row>
        </template>
      </el-table-column>
      <el-table-column prop="roleId" label="角色ID" width="150" align="center"></el-table-column>
      <el-table-column prop="roleName" label="角色名称" width="180" align="center"></el-table-column>
      <el-table-column prop="roleDesc" label="描述" width="270" align="center"></el-table-column>
      <el-table-column label="操作" align="center">
        <template slot-scope="scope">
          <el-button size="mini" plain type="primary" icon="el-icon-edit"
                     @click="handleEditRole(scope.$index, scope.row)">编辑
          </el-button>
          <el-button size="mini" plain type="danger" icon="el-icon-delete" @click="deleteRole(scope.row.roleName)">删除
          </el-button>
          <el-button size="mini" plain type="success" icon="el-icon-check" @click="showRoleDialog(scope.row)">
            分配权限
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
      roleDialogVisible: false,
      roleEditDialogVisible: false,
      roleForm: {
        roleName: '',
        roleDesc: ''
      },
      roleEditForm: {
        roleId: '',
        roleName: '',
        roleDesc: ''
      },
      popoverRole: true,
      total: 0,
      pageSize: 10,
      currentPage: 1,
      rolesList: [],
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
      var _this = this
      var roleName = _this.searchText

      axios.post('restful/roles/select',
        {
          'roleName': roleName === '' ? '' : _this.searchText
        },
        {
          headers: {
            'content-type': 'application/json'
          },
          withCredentials: true
        }).then(function (response) {
        console.log(response)
        _this.rolesList = response.data.roles
        _this.total = response.data.roles.length
      })
        .catch(function (error) {
          console.log(error)
        })
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
          _this.roleDialogVisible = false
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
      axios.post('restful/roles/update', _this.roleEditForm,
        {
          headers: {
            'content-type': 'application/json'
          },
          withCredentials: true
        }).then(function (response) {
        console.log(response)
        if (response.data.returnCode === '1111') {
          _this.roleEditDialogVisible = false
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
    handleEditRole (index, row) {
      this.roleEditDialogVisible = true
      this.roleEditForm = Object.assign({}, row) // 解决修改弹出框数据时，表格数据会发生同步改变
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
