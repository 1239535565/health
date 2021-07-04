<template>
  <div class="app-container calendar-list-container">

    <!-- 查询结果 -->
    <el-table size="small" :data="list" v-loading="listLoading" element-loading-text="正在查询中。。。" border fit
              highlight-current-row>
      <el-table-column label="编号" prop="id" align="center">
        <template slot-scope="scope">
          <span>{{ scope.row.id }}</span>
        </template>
      </el-table-column>
      <el-table-column label="门店名称" align="center">
        <template slot-scope="scope">
          <span>{{ scope.row.name }}</span>
        </template>
      </el-table-column>
      <el-table-column label="logo" align="center">
        <template slot-scope="scope">
          <el-image
            style="width: 100px; height: 70px"
            :src="scope.row.picUrl"
            :preview-src-list="[scope.row.picUrl]">
          </el-image>
        </template>
      </el-table-column>
      <el-table-column label="管理员" align="center">
        <template slot-scope="scope">
          <span>{{ scope.row.userName }}</span>
        </template>
      </el-table-column>
      <el-table-column align="center" width="200" label="操作" class-name="small-padding fixed-width">
        <template slot-scope="scope">
          <el-button type="warning" size="mini"  @click="handleUpdate(scope.row)">修改</el-button>
        </template>
      </el-table-column>
    </el-table>

    <el-dialog title="修改门店" :visible.sync="dialogFormVisible" >
      <el-form :rules="rules" ref="dataForm" :model="dataForm" status-icon label-position="left" label-width="100px" style='width: 400px; margin-left:50px;'>
        <el-form-item label="门店名称" prop="name">
          <el-input v-model="dataForm.name"></el-input>
        </el-form-item>
        <el-form-item label="logo" prop="picUrl">
          <el-upload
            class="avatar-uploader"
            :action="uploadPath"
            :show-file-list="false"
            :on-success="handleAvatarSuccess"
            :before-upload="beforeAvatarUpload"
          >
            <img v-if="this.dataForm.picUrl" :src="this.dataForm.picUrl" class="avatar" />
            <i v-else class="el-icon-plus avatar-uploader-icon"></i>
          </el-upload>
        </el-form-item>
        <el-form-item label="管理员" prop="userId">
          <el-select v-model="dataForm.userId" placeholder="请选择">
            <el-option
              v-for="item in userList"
              :key="item.id"
              :label="item.name"
              :value="item.id">
            </el-option>
          </el-select>
        </el-form-item>
      </el-form>

      <div slot="footer" class="dialog-footer">
        <el-button @click="dialogFormVisible = false">取消</el-button>
        <el-button type="primary" @click="updateData">确定</el-button>
      </div>

    </el-dialog>
  </div>
</template>

<script>
  import { queryAll, upt } from '@/api/store'
  import { queryUser } from '@/api/user'
  import { uploadPath } from '@/api/upload'

  export default {
    name: 'store',
    data() {
      return {
        uploadPath,
        list: null,
        userList: undefined,
        listLoading: true,
        dialogFormVisible: false,
        dataForm: {},
        rules: {}
      }
    },
    created() {
      this.getList()
      this.getUser()
    },
    methods: {
      getList() {
        this.listLoading = true
        queryAll().then(response => {
          this.list = response.data.data
          this.listLoading = false
        }).catch(() => {
          this.list = []
          this.listLoading = false
        })
      },
      getUser() {
        queryUser().then(response => {
          this.userList = response.data.data.data
        })
      },
      handleUpdate(row) {
        this.dataForm = Object.assign({}, row)
        this.dialogFormVisible = true
        this.$nextTick(() => {
          this.$refs['dataForm'].clearValidate()
        })
      },
      handleAvatarSuccess(res, file) {
        this.dataForm.picUrl = res.data
      },
      beforeAvatarUpload(file) {
        const isLt2M = file.size / 1024 / 1024 < 10
        if (!isLt2M) {
          this.$message.error('文件大小不能超过 10MB!')
        }
        return isLt2M
      },
      updateData() {
        this.$refs['dataForm'].validate((valid) => {
          if (valid) {
            upt(this.dataForm).then(() => {
              this.dialogFormVisible = false
              this.$notify({
                title: '成功',
                message: '更新成功',
                type: 'success',
                duration: 2000
              })
              this.getList()
            })
          }
        })
      }
    }
  }
</script>
<style>
  .el-card {
    margin-bottom: 10px;
  }
  .el-tag + .el-tag {
    margin-left: 10px;
  }
  .input-new-keyword {
    width: 90px;
    margin-left: 10px;
    vertical-align: bottom;
  }
  .avatar-uploader .el-upload {
    border: 1px dashed #d9d9d9;
    border-radius: 6px;
    cursor: pointer;
    position: relative;
    overflow: hidden;
  }
  .avatar-uploader .el-upload:hover {
    border-color: #20a0ff;
  }
  .avatar-uploader-icon {
    font-size: 28px;
    color: #8c939d;
    width: 120px;
    height: 120px;
    line-height: 120px;
    text-align: center;
  }
  .avatar {
    width: 120px;
    height: 120px;
    display: block;
  }
</style>
