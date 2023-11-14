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
              @click="searchOldPersons"></el-button>
          </el-input>
        </el-col>
        <el-col :span="4">
          <el-button plain @click="oldDialogVisible = true">添加老人</el-button>
        </el-col>

      </el-row>
    </div>
    <!-- start 添加角色弹窗 -->
    <el-dialog
      title="老人添加"
      :visible.sync="oldDialogVisible"
      @close="closeRoleAddDialog"
      width="30%">
      <el-form label-width="80px" :model="oldForm" :rules="rules"  ref="addoldFormRef">
        <el-form-item label="姓名" prop="name">
          <el-input v-model="oldForm.name"></el-input>
        </el-form-item>
        <!-- <el-form-item label="年龄" prop="age">
          <el-input v-model="oldForm.age"></el-input>
        </el-form-item> -->
		<el-form-item label="性别" prop="sex">
		<el-radio-group v-model="oldForm.sex">
		  <el-radio label="男"></el-radio>
		  <el-radio label="女"></el-radio>
		</el-radio-group>
		</el-form-item>
		<el-form-item label="电话" prop="phone">
		<el-input v-model="oldForm.phone"></el-input>
		</el-form-item>
		<el-form-item label="房间" prop="room">
		<el-input v-model="oldForm.room"></el-input>
		</el-form-item>
		<el-form-item label="病史" prop="illness">
		<el-input v-model="oldForm.illness"></el-input>
		</el-form-item>
		<el-form-item label="过敏药物" prop="warn">
		<el-input v-model="oldForm.warn"></el-input>
		</el-form-item>
		<el-form-item label="喜好食物" prop="foodLike">
		<el-input v-model="oldForm.foodLike"></el-input>
		</el-form-item>
		<el-form-item label="忌口食物" prop="foodHate">
		<el-input v-model="oldForm.foodHate"></el-input>
		</el-form-item>
		<el-form-item label="常用药物" prop="drag">
		<el-input v-model="oldForm.drag"></el-input>
		</el-form-item>
		<el-form-item label="出生日期" prop="bothdate">
		<el-col :span="24">
		  <el-date-picker type="date" placeholder="选择日期" value-format="yyyy-MM-dd" v-model="oldForm.bothdate" style="width: 100%;"></el-date-picker>
		</el-col>
		</el-form-item>
		<el-form-item label="监护人" prop="customerId">
			<el-col :span="48">
				<el-select v-model="oldForm.customerId" placeholder="请选择监护人">
				<el-option v-for="item in options"
      :key="item.customerId"
      :label="item.customerName"
      :value="item.customerId">
			</el-option>
			</el-select>
		</el-col>

		</el-form-item>
<!--			<el-form-item label="手表编号" prop="deviceId">-->
<!--			<el-input v-model="oldForm.deviceId"></el-input>-->
<!--			</el-form-item>-->
      </el-form>
      <span slot="footer" class="dialog-footer">
      <el-button @click="oldDialogVisible = false">取 消</el-button>
      <el-button type="primary" @click="addOldPersons('addoldFormRef')">确 定</el-button>
    </span>
    </el-dialog>
    <!-- end 添加角色弹窗 -->
    <!-- start 编辑角色弹窗 -->
    <el-dialog
      title="角色编辑"
      :visible.sync="oldEditDialogVisible"
      width="30%">
      <el-form label-width="80px" :rules="rules" :model="oldEditForm">
        		<el-form-item label="姓名" prop="name">
        			<el-input v-model="oldEditForm.name"></el-input>
        		</el-form-item>
        		<el-form-item label="年龄" prop="oldId" style="display: none;">
        			<el-input v-model="oldEditForm.oldId"></el-input>
        		</el-form-item>

        <el-form-item label="性别" prop="sex">
        <el-radio-group v-model="oldEditForm.sex">
        	<el-radio label="男"></el-radio>
        	<el-radio label="女"></el-radio>
        </el-radio-group>
        </el-form-item>
        <el-form-item label="familyId" prop="familyId" style="display: none" >
          <el-input v-model="oldEditForm.familyId"></el-input>
        </el-form-item>
        <el-form-item label="电话" prop="phone">
        <el-input v-model="oldEditForm.phone"></el-input>
        </el-form-item>
        <el-form-item label="房间" prop="room">
        <el-input v-model="oldEditForm.room"></el-input>
        </el-form-item>
        <el-form-item label="病史" prop="illness">
        <el-input v-model="oldEditForm.illness"></el-input>
        </el-form-item>
        <el-form-item label="过敏药物" prop="warn">
        <el-input v-model="oldEditForm.warn"></el-input>
        </el-form-item>
        <el-form-item label="喜好食物" prop="foodLike">
        <el-input v-model="oldEditForm.foodLike"></el-input>
        </el-form-item>
        <el-form-item label="忌口食物" prop="foodHate">
        <el-input v-model="oldEditForm.foodHate"></el-input>
        </el-form-item>
        <el-form-item label="常用药物" prop="drag">
        <el-input v-model="oldEditForm.drag"></el-input>
        </el-form-item>
        <el-form-item label="出生日期" prop="bothdate">
        <el-col :span="24">
        	<el-date-picker type="date" placeholder="选择日期" value-format="yyyy-MM-dd" v-model="oldEditForm.bothdate" style="width: 100%;"></el-date-picker>
        </el-col>
        </el-form-item>
        <el-form-item label="监护人" prop="customerId">
        	<el-col :span="48">
        		<el-select v-model="oldEditForm.customerName" placeholder="请选择监护人">
        		<el-option v-for="item in options"
      :key="item.customerId"
      :label="item.customerName"
      :value="item.customerId">
			</el-option>

        	</el-select>
        </el-col>

        </el-form-item>
<!--				<el-form-item label="手表编号" prop="deviceId">-->
<!--				<el-input v-model="oldEditForm.deviceId"></el-input>-->
<!--				</el-form-item>-->
      </el-form>
      <span slot="footer" class="dialog-footer">
      <el-button @click="oldEditDialogVisible = false">取 消</el-button>
      <el-button type="primary" @click="editOldPerson">确 定</el-button>
    </span>
    </el-dialog>
    <!-- end 编辑角色弹窗 -->
    <!-- 表格 -->
    <el-table :data="personDetailList.slice((currentPage-1)*pageSize,currentPage*pageSize)"
    style="width: 100%" border>
    <!-- 可展开行 -->

	<el-table-column type="expand">
			  <template slot-scope="prop">
				<p>家属姓名:{{prop.row.familyName}}</p><p>家属性别:{{prop.row.familySex}}</p>
				<p>关系:{{prop.row.familyRelation}}</p><p>家属电话:{{prop.row.familyPhone}}</p>
				<p>家属地址:{{prop.row.familyAddress}}</p><p>过敏:{{prop.row.warn}}</p>
				<p>喜好食物:{{prop.row.foodLike}}</p><p>忌口食物:{{prop.row.foodHate}}</p>
				<p>常用药物:{{prop.row.drag}}</p><p>病史:{{prop.row.illness}}</p>

			  </template>

	</el-table-column>

      <el-table-column type="index" width="100" align="center"></el-table-column>
      <el-table-column prop="oldId" label="老人id" width="100" align="center"></el-table-column>
      <el-table-column prop="name" label="姓名" width="100" align="center"></el-table-column>
      <el-table-column prop="bothdate" label="出生日期" width="120" align="center"></el-table-column>
	  <el-table-column prop="sex" label="性别" width="100" align="center"></el-table-column>
	  <el-table-column prop="phone" label="电话" width="120" align="center"></el-table-column>
	  <el-table-column prop="room" label="房间" width="100" align="center"></el-table-column>
	  <el-table-column prop="beTime" label="入院时间" width="100" align="center"></el-table-column>
	  <!-- <el-table-column prop="familyName" label="家属姓名" width="100" align="center"></el-table-column>
	  <el-table-column prop="familyRelation" label="关系" width="80" align="center"></el-table-column>
	  <el-table-column prop="familyPhone" label="家属电话" width="120" align="center"></el-table-column>
	  <el-table-column prop="familySex" label="家属性别" width="90" align="center"></el-table-column> -->
		<el-table-column prop="customerName" label="护士" width="100" align="center"></el-table-column>
<!--		<el-table-column prop="deviceId" label="手表编号" width="100" align="center"></el-table-column>-->
<!--      <el-table-column prop="familyId" label="familyId" width="100" align="center" style="display: none"></el-table-column>-->
		<!-- <el-table-column prop="illness" label="病史" width="100" align="center"></el-table-column>
		<el-table-column prop="warn" label="过敏" width="100" align="center"></el-table-column>
		<el-table-column prop="foodLike" label="喜好食物" width="100" align="center"></el-table-column>
		<el-table-column prop="foodHate" label="忌口食物" width="100" align="center"></el-table-column>
		<el-table-column prop="drag" label="常用药物" width="100" align="center"></el-table-column> -->
      <el-table-column label="操作" align="center">
        <template slot-scope="scope">
          <el-button size="mini" plain type="primary" icon="el-icon-edit"
                     @click="handleeditOldPerson(scope.$index, scope.row)">编辑
          </el-button>
          <el-button size="mini" plain type="danger" icon="el-icon-delete" @click="deleteOldPerson(scope.row.oldId)">删除
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
	  // var checkPhone = (rule, value, callback) => {
	  //   const phoneReg = /^1[3|4|5|7|8][0-9]{9}$/
	  //   if (!value) {
	  //     return callback(new Error('电话号码不能为空'))
	  //   }
	  //   setTimeout(() => {
	  //     // Number.isInteger是es6验证数字是否为整数的方法,但是我实际用的时候输入的数字总是识别成字符串
	  //     // 所以我就在前面加了一个+实现隐式转换
    //
	  //     if (!Number.isInteger(+value)) {
	  //       callback(new Error('请输入数字值'))
	  //     } else {
	  //       if (phoneReg.test(value)) {
	  //         callback()
	  //       } else {
	  //         callback(new Error('电话号码格式不正确'))
	  //       }
	  //     }
	  //   }, 100)
	  // }
    return {
      searchText: '', // 角色查询内容
      oldDialogVisible: false,
      oldEditDialogVisible: false,
      oldForm: {
        name: '',
        bothdate: '',
		sex: '',
		phone: '',
		room: '',
		illness: '',
		warn: '',
		foodLike: '',
		foodHate: '',
		drag: '',
		beTime: '',
		customerId: '',
        familyId:"",
		// illness: '',
		// warn: '',
		// foodLike: '',
		// foodHate: '',
		// drag: '',
		deviceId: ''
      },
      oldEditForm: {
       		name: '',
       		bothdate: '',
			oldId: '',
        familyId:"",
       sex: '',
       phone: '',
       room: '',
       illness: '',
       warn: '',
       foodLike: '',
       foodHate: '',
       drag: '',
       beTime: '',
       customerId: '',

			 customerName: '',
			 // illness: '',
			 // warn: '',
			 // foodLike: '',
			 // foodHate: '',
			 // drag: '',
			 deviceId: ''
      },
			options:[],
      popoverRole: true,
      total: 0,
      pageSize: 10,
      currentPage: 1,
      personDetailList: [{
        familyId: "1000",
		  oldId: '1',
		  name: '小黄',
		  bothdate: '1939-03-04',
		  sex: '男',
		  phone: '1345345345',
		  room: '102',
		  beTime: '2019-02-21',
		  familyName: '小刚',
		  familyRelation: '儿子',
		  familyPhone: '12356663',
		  familySex: '男',
		  familyAddress: '南昌',
			customerName: '小泛',
			illness: '心脏病',
			warn: '斑斓更',
			foodLike: '萝卜',
			foodHate: '青菜',
			drag: '定心丸',
			deviceId: '1'
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
	  			// phone: [
	  			//   { validator: checkPhone, trigger: 'blur'}
	  			// ]
	  		  }
    }
  },
  created () {
    this.searchOldPersons()
		this.addCustomerList()
  },
  methods: {
    // 查询
    searchOldPersons () {
      var _this = this
      var name = _this.searchText

      axios.post('restful/oldPerson/selectDetail',
        {
          'name': name === '' ? '' : _this.searchText
        },
        {
          headers: {
            'content-type': 'application/json'
          },
          withCredentials: true
        }).then(function (response) {
        _this.personDetailList = response.data.oldPersonDetails
        _this.total = response.data.oldPersonDetails.length
		console.log(_this.personDetailList)
      })
        .catch(function (error) {
          console.log(error)
        })

    },
    // 添加老人
    addOldPersons () {
      var _this = this
	  var param = _this.oldForm
      axios.post('restful/oldPerson/add',
        param,
        {
          headers: {
            'content-type': 'application/json'
          },
          withCredentials: true
        }).then(function (response) {
        console.log(response)
        if (response.data.returnCode === '1111') {
          _this.oldDialogVisible = false
          _this.searchOldPersons()
          _this.$message({
            type: 'success',
            message: '添加老人成功'
          })
        } else {
          _this.$message({
            type: 'error',
            message: '添加老人失败'
          })
        }
      })
        .catch(function (error) {
          console.log(error)
        })
    },
    // 关闭添加老人对话框
    closeRoleAddDialog () {
      // 重置表单
      this.$refs.addoldFormRef.resetFields()
    },
    // 编辑
    editOldPerson () {
			this.addCustomerList()
      var _this = this
      axios.post('restful/oldPerson/update', _this.oldEditForm,
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
            message: '编辑老人信息成功'
          })
          _this.searchOldPersons()
        } else {
          _this.$message({
            type: 'error',
            message: '编辑老人信息失败'
          })
        }
      })
        .catch(function (error) {
          console.log(error)
        })
    },
		addCustomerList(){
			var _this = this

			  axios.post('restful/customer/select',
			    {
			      'customerName': ""
			    },
			    {
			      headers: {
			        'content-type': 'application/json'
			      },
			      withCredentials: true
			    }).then(function (response) {
			    _this.options = response.data.customers
			  })
			    .catch(function (error) {
			      console.log(error)
			    })

		},
    // 删除
    async deleteOldPerson (oldId) {
      console.log(oldId)

      var _this = this
      try {
        await _this.$confirm('此操作将永久删除该信息, 是否继续?', '提示', {
          confirmButtonText: '确定',
          cancelButtonText: '取消',
          type: 'warning'
        })
        console.log(oldId)
        axios.post('restful/oldPerson/delete', {'oldId': oldId},
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
          _this.searchOldPersons()
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
    handleeditOldPerson (index, row) {
      this.oldEditDialogVisible = true
      this.oldEditForm = Object.assign({}, row) // 解决修改弹出框数据时，表格数据会发生同步改变
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
