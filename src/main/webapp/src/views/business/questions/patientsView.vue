<template>
  <div class="app-container">
    <el-form :model="queryParams" ref="queryForm" :inline="true" label-width="68px">
      <el-form-item label="问题" prop="title">
        <el-input
          v-model="queryParams.title"
          placeholder="请输入问题"
          clearable
          size="small"
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>

      <el-form-item>
        <el-button type="primary" icon="el-icon-search" size="mini" @click="handleQuery">搜索</el-button>
        <el-button icon="el-icon-refresh" size="mini" @click="resetQuery">重置</el-button>
      </el-form-item>
    </el-form>

    <el-table v-loading="loading" :data="questionsList" @selection-change="handleSelectionChange">
      <el-table-column type="selection" width="55" align="center" />
      <el-table-column label="序号" align="center" prop="userId" width="80">
        <template slot-scope="scope">{{scope.$index+1}}</template>
      </el-table-column>
      <el-table-column label="患者姓名" align="center" prop="patientName" />
      <el-table-column label="患者性别" align="center" prop="ssex">
        <template slot-scope="scope">
          <el-tag v-if="scope.row.ssex==0" type="success">男</el-tag>
          <el-tag v-if="scope.row.ssex==1" type="danger">女</el-tag>
          <el-tag v-if="scope.row.ssex==2" type="danger">未知</el-tag>
        </template>
      </el-table-column>
      <el-table-column label="患者生日" align="center" prop="birthday">
        <template slot-scope="scope">
          <span>{{ parseTime(scope.row.birthday, '{y}-{m}-{d}') }}</span>
        </template>
      </el-table-column>
      <el-table-column label="问题" align="center" prop="title" />
      <el-table-column label="创建时间" align="center" prop="createTime" width="180">
        <template slot-scope="scope">
          <span>{{ parseTime(scope.row.createTime, '{y}-{m}-{d} {h}:{i}:{s}') }}</span>
        </template>
      </el-table-column>
      <el-table-column label="操作" align="center" class-name="small-padding fixed-width" width="280">
        <template slot-scope="scope">
          <el-button size="mini" type="text" icon="el-icon-view" @click="handGetAsk(scope.row)">查看回答</el-button>
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
    <el-dialog width="60%" title="回答列表" :visible.sync="askVisible">
      <el-table :data="askList">
        <el-table-column label="ID" type="index" align="center" />
        <el-table-column prop="replyName" label="医生名字" :show-overflow-tooltip="true" />
        <el-table-column prop="reply" label="回复内容" :show-overflow-tooltip="true" />
        <el-table-column prop="replyTime" label="回复时间" :show-overflow-tooltip="true" />
        <el-table-column
          label="操作"
          align="center"
          class-name="small-padding fixed-width"
          width="280"
        >
          <template slot-scope="scope">
            <el-button
              size="mini"
              type="text"
              icon="el-icon-view"
              @click="handGetDoctorInfo(scope.row)"
            >查看医生详情</el-button>
          </template>
        </el-table-column>
      </el-table>
    </el-dialog>

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
          <el-col :span="8">
            <el-form-item label="是否启用">
              <el-radio-group v-model="form.status" disabled>
                <el-radio :label="1">启用</el-radio>
                <el-radio :label="0">禁用</el-radio>
              </el-radio-group>
            </el-form-item>
          </el-col>
          <el-col :span="8">
            <el-form-item label="角色" prop="roleName">
              <el-input v-model="form.roleName" placeholder="无角色" maxlength="11" disabled />
            </el-form-item>
          </el-col>
          <el-col :span="8">
            <el-form-item label="创建时间">
              <el-input v-model="form.createTime" placeholder disabled></el-input>
            </el-form-item>
          </el-col>
          <el-col :span="8">
            <el-form-item label="修改时间">
              <el-input v-model="form.modifyTime" placeholder disabled></el-input>
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

 <!-- 查看用户详情对话框 -->
    <el-dialog title="用户详情" :visible.sync="userInfo" width="800px">
      <el-form ref="form" :model="formData" label-width="80px">
        <el-row>
          <el-col :span="8">
            <el-form-item label="用户名" prop="username">
              <el-input v-model="formData.username" placeholder disabled />
            </el-form-item>
          </el-col>
          <el-col :span="8">
            <el-form-item label="用户昵称" prop="nickName">
              <el-input v-model="formData.nickName" placeholder disabled />
            </el-form-item>
          </el-col>
          <el-col :span="8">
            <el-form-item label="手机号码" prop="mobile">
              <el-input v-model="formData.mobile" placeholder maxlength="11" disabled />
            </el-form-item>
          </el-col>
          <el-col :span="8">
            <el-form-item label="邮箱" prop="email" disabled>
              <el-input v-model="formData.email" placeholder maxlength="50" disabled />
            </el-form-item>
          </el-col>
          <el-col :span="8">
            <el-form-item label="用户性别">
              <el-select v-model="formData.ssex" placeholder="请选择" disabled>
                <el-option v-for="item in ssex" :key="item.id" :label="item.lable" :value="item.id"></el-option>
              </el-select>
            </el-form-item>
          </el-col>
          <el-col :span="8">
            <el-form-item label="所属科室" prop="email" disabled>
              <el-input v-model="formData.depName" placeholder maxlength="50" disabled />
            </el-form-item>
          </el-col>
          <el-col :span="24">
            <el-form-item label="履历详情">
              <el-input v-model="formData.description" type="textarea" placeholder disabled></el-input>
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
import { listQuestions, delQuestions, reply } from "@/api/questions";
import { getDoctorInfoByUsername } from "@/api/common";
import { Message, MessageBox } from "element-ui";

export default {
  name: "Questions",
  data() {
    return {
      // 遮罩层
      loading: true,
      userInfo: false,
      // 选中数组
      titles: [],
      ids: [],
      // 非单个禁用
      single: true,
      // 非多个禁用
      multiple: true,
      // 总条数
      total: 0,
      // 问答表格数据
      questionsList: [],
      askVisible: false,
      askList: null,
      // 弹出层标题
      title: "",
      // 是否显示弹出层
      open: false,
      // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        title: undefined,
        patientName: undefined
      },
      // 表单参数
      form: {},
      formData: {},
      // 表单校验
      rules: {}
    };
  },
  created() {
    this.getList();
  },
  methods: {
    /** 查询问答列表 */
    getList() {
      this.loading = true;
      listQuestions(this.queryParams).then(response => {
        this.questionsList = response.data.data.records;
        this.total = response.data.data.total;
        this.loading = false;
      });
    },
    // 取消按钮
    cancel() {
      this.open = false;
      this.reset();
      this.userInfo = false
    },
    // 表单重置
    reset() {
      this.form = {
        id: undefined,
        title: undefined,
        parentId: undefined,
        doctorId: undefined,
        patiensId: undefined,
        createTime: undefined,
        modifyTime: undefined,
        reply: undefined
      };
      this.resetForm("form");
    },
    /** 搜索按钮操作 */
    handleQuery() {
      this.queryParams.pageNum = 1;
      this.getList();
    },
    /** 重置按钮操作 */
    resetQuery() {
      this.resetForm("queryForm");
      this.handleQuery();
    },
    // 多选框选中数据
    handleSelectionChange(selection) {
      this.titles = selection.map(item => item.title);
      this.ids = selection.map(item => item.id);
      this.single = selection.length != 1;
      this.multiple = !selection.length;
    },
    handGetAsk(row) {
      this.reset();
      this.askList = row.questions;
      this.askVisible = true;
    },
    handGetDoctorInfo(row){
      getDoctorInfoByUsername(row.replyName).then(res => {
        this.formData = res.data.data
        this.userInfo = true
      })
    }
  }
};
</script>