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
              @click="searchfamily"></el-button>
          </el-input>
        </el-col>
        <el-col :span="4">
          <el-button plain @click="customerDialogVisible = true">账单添加</el-button>
        </el-col>
      </el-row>
    </div>
    <!-- start 添加角色弹窗 -->
    <el-dialog
      title="账单添加"
      :visible.sync="customerDialogVisible"
      @close="closeRoleAddDialog"
      width="30%">
      <el-form label-width="80px" :model="familyForm" :rules="rules" class="demo-ruleForm"  ref="addfamilyFormRef">
        <el-form-item label="老人id" prop="oldid">
          <el-input v-model="familyForm.oldid"></el-input>
        </el-form-item>
        <el-form-item label="姓名" prop="name">
          <el-input v-model="familyForm.name"></el-input>
        </el-form-item>
        <el-form-item label="事件" prop="thing">
          <el-input v-model="familyForm.thing"></el-input>
        </el-form-item>
        <el-form-item label="时间" prop="time">
          <el-input v-model="familyForm.time"></el-input>
        </el-form-item>
        <el-form-item label="费用(元)" prop="money">
          <el-input v-model="familyForm.money"></el-input>
        </el-form-item>
        <el-form-item label="状况" prop="orderstatus">
          <el-input v-model="familyForm.orderstatus"></el-input>
        </el-form-item>
<!--        <el-form-item label="关系" prop="familyRelation">-->
<!--          <el-input v-model="familyForm.familyRelation"></el-input>-->
<!--        </el-form-item>-->
<!--        <el-form-item label="性别" prop="familySex">-->
<!--          <el-radio-group v-model="familyForm.familySex">-->
<!--            <el-radio label="男"></el-radio>-->
<!--            <el-radio label="女"></el-radio>-->
<!--          </el-radio-group>-->
<!--        </el-form-item>-->
<!--        <el-form-item label="电话" prop="familyPhone">-->
<!--          <el-input v-model="familyForm.familyPhone"></el-input>-->
<!--        </el-form-item>-->
<!--        <el-form-item label="地址" prop="familyAddress">-->
<!--          <el-input v-model="familyForm.familyAddress"></el-input>-->
<!--        </el-form-item>-->
<!--        <el-form-item label="老人电话" prop="oldPhone">-->
<!--          <el-input v-model="familyForm.oldPhone"></el-input>-->
<!--        </el-form-item>-->
      </el-form>
      <span slot="footer" class="dialog-footer">
      <el-button @click="customerDialogVisible = false">取 消</el-button>
      <el-button type="primary" @click="addFamily('addfamilyFormRef')">确 定</el-button>
    </span>
    </el-dialog>
    <!-- end 添加角色弹窗 -->
    <!-- start 编辑角色弹窗 -->
    <el-dialog
      title="账单编辑"
      :visible.sync="oldEditDialogVisible"
      width="30%">
      <el-form label-width="80px" :rules="rules" :model="familyEditForm">
        <el-form-item label="老人id" prop="oldid">
          <el-input v-model="familyEditForm.oldid"></el-input>
        </el-form-item>
        <el-form-item label="姓名" prop="name">
          <el-input v-model="familyEditForm.name"></el-input>
        </el-form-item>
<!--        <el-form-item label="事件" prop="thing" style="display: none;">-->
        <el-form-item label="事件" prop="thing" >
          <el-input v-model="familyEditForm.thing"></el-input>
        </el-form-item>
        <el-form-item label="时间" prop="time">
          <el-input v-model="familyEditForm.time"></el-input>
        </el-form-item>
        <el-form-item label="费用(元)" prop="money">
          <el-input v-model="familyEditForm.money"></el-input>
        </el-form-item>
        <el-form-item label="状况" prop="orderstatus">
          <el-input v-model="familyEditForm.orderstatus"></el-input>
        </el-form-item>
      </el-form>
      <span slot="footer" class="dialog-footer">
      <el-button @click="oldEditDialogVisible = false">取 消</el-button>
      <el-button type="primary" @click="editFamily">确 定</el-button>
    </span>
    </el-dialog>
    <!-- end 编辑角色弹窗 -->
    <!-- 表格 -->
    <el-table :data="familyList.slice((currentPage-1)*pageSize,currentPage*pageSize)"
              style="width: 100%" border>
      <!-- 可展开行 -->

      <el-table-column type="index" width="100" align="center"></el-table-column>
      <el-table-column prop="oldid" label="老人id" width="140" align="center"></el-table-column>
      <el-table-column prop="name" label="姓名" width="140" align="center"></el-table-column>
      <el-table-column prop="orderid" label="事件id" width="140" align="center"></el-table-column>
      <el-table-column prop="thing" label="事件" width="140" align="center"></el-table-column>
      <el-table-column prop="time" label="时间" width="140" align="center"></el-table-column>
      <el-table-column prop="money" label="费用(元)" width="140" align="center"></el-table-column>
      <el-table-column prop="orderstatus" label="状况" width="140" align="center"></el-table-column>
      <el-table-column label="操作" align="center">
        <template slot-scope="scope">
          <el-button size="mini" plain type="primary" icon="el-icon-edit"
                     @click="handleeditFamily(scope.$index, scope.row)">编辑
          </el-button>
          <el-button size="mini" plain type="danger" icon="el-icon-delete" @click="deleteFamily(scope.row.orderid)">删除
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
    var checkPhone = (rule, value, callback) => {
      const phoneReg = /^1[3|4|5|7|8][0-9]{9}$/
      if (!value) {
        return callback(new Error('电话号码不能为空'))
      }
      setTimeout(() => {
        // Number.isInteger是es6验证数字是否为整数的方法,但是我实际用的时候输入的数字总是识别成字符串
        // 所以我就在前面加了一个+实现隐式转换

        if (!Number.isInteger(+value)) {
          callback(new Error('请输入数字值'))
        } else {
          if (phoneReg.test(value)) {
            callback()
          } else {
            callback(new Error('电话号码格式不正确'))
          }
        }
      }, 100)
    }
    return {
      searchText: '', // 角色查询内容
      customerDialogVisible: false,
      oldEditDialogVisible: false,
      familyForm: {
        orderid:'',
        oldid:'',
        name: '',
        thing: '',
        time: '',
        money: '',
        orderstatus:'',
        // familyAddress: '',
        // oldPhone: ''
      },
      familyEditForm: {
        name:'',
        oldid:'',
        thing: '',
        time:'',
        money:'',
        familyName: '',
        orderid:'',
        customerName: '',
        customerAge: '',
        customerSex: '',
        customerPhone: '',
        customerAddress: '',
        customerId: '',
        orderstatus:'',
      },
      popoverRole: true,
      total: 0,
      pageSize: 10,
      currentPage: 1,
      familyList: [{
        orderstatus:'未完成',
        orderid:'',
        name:'小刚',
        oldid:'1',
        thing: '尿不湿3片',
        time:'2020-3-12',
        money:'120元',
        familyName: '小黄',
        familyRelation: '儿子',
        familySex: '男',
        familyPhone: '15415263698',
        familyAddress: '南昌'
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
      roleId: -1,
      rules: {
        familyPhone: [
          { validator: checkPhone, trigger: 'blur'}
        ],
        oldPhone: [
          { validator: checkPhone, trigger: 'blur'}
        ]
      }
    }
  },
  created () {
    this.searchfamily()
  },
  methods: {
    // 查询
    searchfamily () {
      var _this = this
      var familyName = _this.searchText
      axios.post('restful/cost/select',
        {
          'familyName': familyName === '' ? '' : _this.searchText
        },
        {
          headers: {
            'content-type': 'application/json'
          },
          withCredentials: true
        }).then(function (response) {
        _this.familyList = response.data.costs
        _this.total = response.data.costs.length
        console.log(_this.familyList)
      })
        .catch(function (error) {
          console.log(error)
        })

    },
    // 添加账单
    addFamily () {
      var _this = this
      axios.post('restful/cost/add',
        _this.familyForm,
        {
          headers: {
            'content-type': 'application/json'
          },
          withCredentials: true
        }).then(function (response) {
        console.log(response)
        if (response.data.returnCode === '1111') {
          _this.customerDialogVisible = false
          _this.searchfamily()
          _this.$message({
            type: 'success',
            message: '账单添加成功'
          })
        } else {
          _this.$message({
            type: 'error',
            message: '账单添加失败'
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
      this.$refs.addfamilyFormRef.resetFields()
    },
    // 编辑账单
    editFamily () {
      var _this = this
      axios.post('restful/cost/updateCost', _this.familyEditForm,
        {
          headers: {
            'content-type': 'application/json'
          },
          withCredentials: true
        }).then(function (response) {
        console.log(response)
        if (response.data.returnCode === '1111') {
          _this.oldEditDialogVisible = false
          _this.$message({
            type: 'success',
            message: '编辑账单成功'
          })
          _this.searchfamily()
        } else {
          _this.$message({
            type: 'error',
            message: '编辑账单失败'
          })
        }
      })
        .catch(function (error) {
          console.log(error)
        })
    },
    // 删除
    async deleteFamily (familyId) {
      console.log(familyId)

      var _this = this
      try {
        await _this.$confirm('此操作将永久删除该文件, 是否继续?', '提示', {
          confirmButtonText: '确定',
          cancelButtonText: '取消',
          type: 'warning'
        })
        console.log(familyId)
        axios.post('restful/cost/delete', {'orderid': familyId},
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
              message: '删除成功'
            })
          }
          _this.searchfamily()
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
    handleeditFamily (index, row) {
      this.oldEditDialogVisible = true
      this.familyEditForm = Object.assign({}, row) // 解决修改弹出框数据时，表格数据会发生同步改变
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

