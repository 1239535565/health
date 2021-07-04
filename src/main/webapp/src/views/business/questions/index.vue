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
     
      <el-form-item label="提问患者" prop="patientName">
        <el-input
          v-model="queryParams.patientName"
          placeholder="请输入提问患者姓名"
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

    <el-row :gutter="10" class="mb8">
      <el-col :span="1.5">
        <el-button
          type="danger"
          icon="el-icon-delete"
          size="mini"
          :disabled="multiple"
          @click="handleDelete"
        >删除</el-button>
      </el-col>
    </el-row>

    <el-table v-loading="loading" :data="questionsList" @selection-change="handleSelectionChange">
      <el-table-column type="selection" width="55" align="center" />
      <el-table-column label="序号" align="center" prop="userId" width="80">
            <template slot-scope="scope">
              {{scope.$index+1}}
            </template>
          </el-table-column>
      <el-table-column label="患者姓名" align="center" prop="patientName" />
      <el-table-column label="患者性别" align="center" prop="ssex" >
        <template slot-scope="scope">
          <el-tag v-if="scope.row.ssex==0" type="success">男</el-tag>
          <el-tag v-if="scope.row.ssex==1" type="danger">女</el-tag>
          <el-tag v-if="scope.row.ssex==2" type="danger">未知</el-tag>
        </template>
      </el-table-column>
      <el-table-column label="患者生日" align="center" prop="birthday" >
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
          <el-button
            size="mini"
            type="text"
            icon="el-icon-edit"
            @click="handleUpdate(scope.row)"
          >回答</el-button>
          <el-button
            size="mini"
            type="text"
            icon="el-icon-view"
            @click="handGetAsk(scope.row)"
          >查看回答</el-button>
          <el-button
            size="mini"
            type="text"
            icon="el-icon-delete"
            @click="handleDelete(scope.row)"
          >删除</el-button>
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

    <!-- 回答对话框 -->
    <el-dialog :title="title" :visible.sync="open" width="500px" append-to-body>
      <el-form ref="form" :model="form" :rules="rules" label-width="80px">
        
        <el-form-item label="回复内容" prop="reply">
          <el-input v-model="form.reply" placeholder="请输入回复内容" type="textarea"/>
        </el-form-item>
        
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button type="primary" @click="submitForm">确 定</el-button>
        <el-button @click="cancel">取 消</el-button>
      </div>
    </el-dialog>

    <el-dialog
      width="60%"
      title="回答列表"
      :visible.sync="askVisible" >
      <el-table
          :data="askList"
        >
          <el-table-column
            label="ID"
            type="index"
            align="center"
          />
          <el-table-column
            prop="replyName"
            label="医生名字"
            :show-overflow-tooltip="true"
          />
           <el-table-column
            prop="reply"
            label="回复内容"
            :show-overflow-tooltip="true"
          />
           <el-table-column
            prop="replyTime"
            label="回复时间"
            :show-overflow-tooltip="true"
          />
        </el-table>
    </el-dialog>
  </div>
</template>

<script>
import { listQuestions, delQuestions, reply } from "@/api/questions";
import { Message, MessageBox } from "element-ui"

export default {
  name: "Questions",
  data() {
    return {
      // 遮罩层
      loading: true,
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
      // 表单校验
      rules: {
      }
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
      this.titles = selection.map(item => item.title)
      this.ids = selection.map(item => item.id)
      this.single = selection.length!=1
      this.multiple = !selection.length
    },
    /** 修改按钮操作 */
    handleUpdate(row) {
      this.reset();
      const id = row.id || this.ids
      this.form = row;
      this.open = true;
      this.title = "回答问题";
    },
    handGetAsk(row) {
      this.reset();
      this.askList = row.questions
      this.askVisible = true
    },
    /** 提交按钮 */
    submitForm: function() {
      this.$refs["form"].validate(valid => {
        if (valid) {
          if (this.form.id != undefined) {
            reply(this.form).then(response => {
              if (response.data.status === 200) {
                Message({
                message: "回答成功",
                type: "success",
                duration: 5 * 1000
              });
                this.open = false;
                this.getList();
              } else {
                this.msgError(response.msg);
              }
            });
          }
        }
      });
    },
    /** 删除按钮操作 */
    handleDelete(row) {
      const titles = row.title || this.titles;
      const ids = row.id || this.ids;
      this.$confirm('是否确认删除问答为"' + titles + '"的数据项?', "警告", {
          confirmButtonText: "确定",
          cancelButtonText: "取消",
          type: "warning"
        }).then(function() {
          return delQuestions(ids);
        }).then(() => {
          this.getList();
          Message({
                message: "删除成功",
                type: "success",
                duration: 5 * 1000
              });
        }).catch(function() {});
    }
  }
};
</script>