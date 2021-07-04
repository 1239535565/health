<template>
  <div class="app-container">
    <el-row :gutter="20">
      <!--用户数据-->
      <el-col :span="24" :xs="24">
        <el-form :model="queryParams" ref="queryForm" :inline="true" label-width="68px">
          <el-form-item label="用户名称" prop="username">
            <el-input
              v-model="queryParams.username"
              placeholder="请输入用户名称"
              clearable
              size="small"
              style="width: 240px"
              @keyup.enter.native="handleQuery"
            />
          </el-form-item>
          <el-form-item label="手机号码" prop="mobile">
            <el-input
              v-model="queryParams.mobile"
              placeholder="请输入手机号码"
              clearable
              size="small"
              style="width: 240px"
              @keyup.enter.native="handleQuery"
            />
          </el-form-item>

          <el-form-item label="创建时间">
            <el-date-picker
              v-model="dateRange"
              size="small"
              style="width: 240px"
              value-format="yyyy-MM-dd"
              type="daterange"
              range-separator="-"
              start-placeholder="开始日期"
              end-placeholder="结束日期"
            ></el-date-picker>
          </el-form-item>
          <el-form-item label="科室">
            <el-select
              v-model="queryParams.depId"
              placeholder="请选择科室"
              style="width: 240px;"
              @change="$forceUpdate()"
            >
              <el-option
                v-for="item in departmentOptions"
                :key="item.id"
                :label="item.depName"
                :value="item.id"
              />
            </el-select>
          </el-form-item>
          <el-form-item>
            <el-button
              type="primary"
              icon="el-icon-search"
              size="mini"
              @click="handleQuery"
            >搜索</el-button>
            <el-button icon="el-icon-refresh" size="mini" @click="resetQuery">重置</el-button>
          </el-form-item>
        </el-form>

        <el-table v-loading="loading" :data="userList" @selection-change="handleSelectionChange">
          <el-table-column type="selection" width="29" align="center" />
          <el-table-column label="序号" align="center" prop="userId" width="80">
            <template slot-scope="scope">{{scope.$index+1}}</template>
          </el-table-column>
          <el-table-column
            label="用户名"
            align="center"
            prop="username"
            :show-overflow-tooltip="true"
          />
          <el-table-column
            label="真实姓名"
            align="center"
            prop="nickName"
            :show-overflow-tooltip="true"
          />
          <el-table-column
            label="所属科室"
            align="center"
            prop="depName"
            :show-overflow-tooltip="true"
          />
          <el-table-column label="医生照片" align="center">
            <template slot-scope="scope">
              <img style="width: 29px;height:29px;vertical-align: middle; "
                :src="baseUrl + scope.row.avatar"
                :preview-src-list="[baseUrl + scope.row.avatar]"/>
            </template>
          </el-table-column>
          <el-table-column
            label="毕业院校"
            align="center"
            prop="school"
            width="180"
            :show-overflow-tooltip="true"
          />
          <el-table-column label="手机号码" align="center" prop="mobile" :show-overflow-tooltip="true" />
          <el-table-column
            label="邮箱"
            align="center"
            prop="email"
            width="150"
            :show-overflow-tooltip="true"
          />
          <el-table-column label="生日" align="center" prop="birthday" :show-overflow-tooltip="true">
            <template slot-scope="scope">
              <span>{{ parseTime(scope.row.birthday, '{y}-{m}-{d}') }}</span>
            </template>
          </el-table-column>
          <el-table-column
            label="创建时间"
            align="center"
            prop="createTime"
            width="200"
            :show-overflow-tooltip="true"
          >
            <template slot-scope="scope">
              <span>{{ parseTime(scope.row.createTime) }}</span>
            </template>
          </el-table-column>
          <el-table-column
            label="操作"
            align="center"
            width="100"
            class-name="small-padding fixed-width"
          >
            <template slot-scope="scope">
              <el-button
                size="mini"
                type="text"
                icon="el-icon-view"
                @click="handleGetUserInfo(scope.row)"
              >查看</el-button>
            </template>
          </el-table-column>
        </el-table>

        <pagination
          v-show="total>0"
          :total="total"
          :page.sync="queryParams.pageNum"
          :limit.sync="queryParams.pageSize"
          @pagination="getList"
        />
      </el-col>
    </el-row>

    <!-- 查看用户详情对话框 -->
    <el-dialog title="用户详情" :visible.sync="userInfo" width="800px">
      <el-form ref="form" :model="form" label-width="80px">
        <el-row>
          <el-col :span="8">
            <el-form-item label="用户名" prop="username">
              <el-input v-model="form.username" placeholder disabled />
            </el-form-item>
          </el-col>
          <el-col :span="8">
            <el-form-item label="用户昵称" prop="nickName">
              <el-input v-model="form.nickName" placeholder disabled />
            </el-form-item>
          </el-col>
          <el-col :span="8">
            <el-form-item label="手机号码" prop="mobile">
              <el-input v-model="form.mobile" placeholder maxlength="11" disabled />
            </el-form-item>
          </el-col>
          <el-col :span="8">
            <el-form-item label="邮箱" prop="email" disabled>
              <el-input v-model="form.email" placeholder maxlength="50" disabled />
            </el-form-item>
          </el-col>
          <el-col :span="8">
            <el-form-item label="用户性别">
              <el-select v-model="form.ssex" placeholder="请选择" disabled>
                <el-option v-for="item in ssex" :key="item.id" :label="item.lable" :value="item.id"></el-option>
              </el-select>
            </el-form-item>
          </el-col>
          <el-col :span="24">
            <el-form-item label="履历详情">
              <el-input v-model="form.description" type="textarea" placeholder disabled></el-input>
            </el-form-item>
          </el-col>
        </el-row>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button @click="cancel">取 消</el-button>
      </div>
    </el-dialog>
  </div>
</template>

<script>
import {
  listUser,
  getUser,
  delUser,
  addUser,
  updateUser,
  exportUser,
  resetUserPwd,
  changeUserStatus
} from "@/api/business/doctor";
import { checkUsername, getDepartment } from "@/api/common";
import { getToken } from "@/utils/auth";
import { Message } from "element-ui";

export default {
  name: "User",
  data() {
    var valideteUsername = (rule, value, callback) => {
      if (!value) {
        return callback(new Error("用户名称不能为空"));
      }
      checkUsername(value).then(response => {
        if (response.data.data) {
          callback(new Error("用户名已经存在，不能重复添加"));
        } else {
          callback();
        }
      });
    };
    return {
      baseUrl: 'http://localhost:8081/adminApi',
      // 遮罩层
      loading: true,
      // 选中数组
      usernames: [],
      // 选中数组
      userIds: [],
      // 非单个禁用
      single: true,
      // 非多个禁用
      multiple: true,
      // 总条数
      total: 0,
      // 用户表格数据
      userList: null,
      // 弹出层标题
      title: "",
      // 部门树选项
      // deptOptions: undefined,
      // 是否显示弹出层
      addOpen: false,
      updateOpen: false,
      userInfo: false,
      // 部门名称
      // deptName: undefined,
      // 默认密码
      initPassword: undefined,
      // 日期范围
      dateRange: [],
      // 状态数据字典
      statusOptions: [],
      // 性别状态字典
      sexOptions: [],
      // 岗位选项
      postOptions: [],
      // 角色选项
      roleOptions: [],
      departmentOptions: undefined,
      // 表单参数
      form: {},
      defaultProps: {
        children: "children",
        label: "label"
      },
      // 用户导入参数
      upload: {
        // 是否显示弹出层（用户导入）
        addOpen: false,
        updateOpen: false,
        // 弹出层标题（用户导入）
        title: "",
        // 是否禁用上传
        isUploading: false,
        // 是否更新已经存在的用户数据
        updateSupport: 0,
        // 设置上传的请求头部
        // headers: { Authorization: "Bearer " + getToken() },
        headers: { Authorization: getToken() },
        // 上传的地址
        url: process.env.VUE_APP_BASE_API + "/system/user/importData"
      },
      ssex: [
        {
          id: 0,
          lable: "男"
        },
        {
          id: 1,
          lable: "女"
        },
        {
          id: 2,
          lable: "未知"
        }
      ],
      // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        username: undefined,
        mobile: undefined,
        depId: ""
      }
    };

    var validatePass = (rule, value, callback) => {
      if (value === "") {
        callback(new Error("请输入密码"));
      } else {
        if (this.ruleForm.username !== "") {
          this.$refs.ruleForm.validateField("checkPass");
        }
        callback();
      }
    };
  },
  watch: {
    // 根据名称筛选部门树
    deptName(val) {
      this.$refs.tree.filter(val);
    }
  },
  created() {
    this.getList();
    getDepartment().then(response => {
      this.departmentOptions = response.data.data;
    });
  },
  methods: {
    /** 查询用户列表 */
    getList() {
      this.loading = true;
      listUser(this.addDateRange(this.queryParams, this.dateRange)).then(
        response => {
          this.userList = response.data.data.records;
          this.total = response.data.data.total;
          this.loading = false;
        }
      );
    },
    // 筛选节点
    filterNode(value, data) {
      if (!value) return true;
      return data.label.indexOf(value) !== -1;
    },
    // 节点单击事件
    handleNodeClick(data) {
      this.queryParams.deptId = data.id;
      this.getList();
    },
    // 取消按钮
    cancel() {
      this.addOpen = false;
      this.updateOpen = false;
      this.userInfo = false;
      this.reset();
    },
    // 表单重置
    reset() {
      this.form = {
        userId: undefined,
        username: undefined,
        nickName: undefined,
        // password: undefined,
        mobile: undefined,
        depId: undefined,
        email: undefined,
        ssex: 2,
        status: 0,
        description: undefined,
        roleIds: []
      };
      this.resetForm("form");
    },
    /** 搜索按钮操作 */
    handleQuery() {
      this.queryParams.page = 1;
      this.getList();
    },
    /** 重置按钮操作 */
    resetQuery() {
      this.dateRange = [];
      this.resetForm("queryForm");
      this.queryParams.depId = undefined;
      this.handleQuery();
    },
    // 多选框选中数据
    handleSelectionChange(selection) {
      this.usernames = selection.map(item => item.username);
      this.userIds = selection.map(item => item.userId);
      this.single = selection.length != 1;
      this.multiple = !selection.length;
    },
    handleGetUserInfo(row) {
      const userId = row.userId;
      getUser(userId).then(response => {
        this.form = response.data.data
      });
      this.userInfo = true;
    }
  }
};
</script>