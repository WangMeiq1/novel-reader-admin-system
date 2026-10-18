<template>
  <div style="padding: 20px;">
    <h2>成绩管理</h2>

    <!-- 搜索栏 -->
    <el-row :gutter="20" style="margin-bottom: 20px;">
      <el-col :span="6">
        <el-input
          v-model="keyword"
          placeholder="输入学生姓名或学号搜索"
          clearable
          @clear="loadData"
        />
      </el-col>
      <el-col :span="4">
        <el-button type="primary" @click="loadData">搜索</el-button>
        <el-button type="success" @click="openAddDialog">录入成绩</el-button>
        <el-button type="warning" @click="openStatistics">查看统计</el-button>
      </el-col>
    </el-row>

    <!-- 数据表格 -->
    <el-table :data="tableData" border style="width: 100%">
      <el-table-column prop="scoreId" label="成绩ID" width="100" align="center" />
      <el-table-column prop="studentId" label="学号" width="150" />
      <el-table-column prop="studentName" label="姓名" width="120" />
      <el-table-column prop="courseName" label="课程" min-width="160" />
      <el-table-column prop="score" label="分数" width="120" align="center">
        <template #default="scope">
          <el-tag :type="getScoreTag(scope.row.score)">
            {{ scope.row.score }}
          </el-tag>
        </template>
      </el-table-column>
      <el-table-column label="操作" width="200" align="center" fixed="right">
        <template #default="scope">
          <el-button size="small" type="primary" @click="openEditDialog(scope.row)">编辑</el-button>
          <el-button size="small" type="danger" @click="handleDelete(scope.row.scoreId)">删除</el-button>
        </template>
      </el-table-column>
    </el-table>

    <!-- 分页 -->
    <el-pagination
      v-model:current-page="pageNum"
      v-model:page-size="pageSize"
      :page-sizes="[5, 10, 20]"
      layout="total, sizes, prev, pager, next, jumper"
      :total="total"
      @size-change="loadData"
      @current-change="loadData"
      style="margin-top: 20px; justify-content: flex-end;"
    />

    <!-- 添加/编辑对话框 -->
    <el-dialog v-model="dialogVisible" :title="dialogTitle" width="500px">
      <el-form ref="formRef" :model="formData" :rules="rules" label-width="100px">
        <el-form-item label="学号" prop="studentId">
          <el-input v-model="formData.studentId" placeholder="请输入学号" />
        </el-form-item>
        <el-form-item label="课程编号" prop="courseId">
          <el-input-number v-model="formData.courseId" :min="1" placeholder="请输入课程编号" />
        </el-form-item>
        <el-form-item label="分数" prop="score">
          <el-input-number v-model="formData.score" :min="0" :max="100" />
        </el-form-item>
      </el-form>
      <template #footer>
        <el-button @click="dialogVisible = false">取消</el-button>
        <el-button type="primary" @click="submitForm">确认</el-button>
      </template>
    </el-dialog>

    <!-- 统计对话框 -->
    <el-dialog v-model="statisticsVisible" title="成绩统计" width="600px">
      <el-row :gutter="20">
        <el-col :span="12">
          <el-statistic title="选课人数" :value="statistics.count" />
        </el-col>
        <el-col :span="12">
          <el-statistic title="平均分" :value="statistics.avg" suffix="分" />
        </el-col>
      </el-row>
      <el-row :gutter="20" style="margin-top: 20px;">
        <el-col :span="8">
          <el-statistic title="最高分" :value="statistics.max" suffix="分" />
        </el-col>
        <el-col :span="8">
          <el-statistic title="最低分" :value="statistics.min" suffix="分" />
        </el-col>
        <el-col :span="8">
          <el-statistic title="及格率" :value="statistics.passRate" suffix="%" />
        </el-col>
      </el-row>
      <template #footer>
        <el-button @click="statisticsVisible = false">关闭</el-button>
      </template>
    </el-dialog>
  </div>
</template>

<script setup>
import { ref, reactive, onMounted } from 'vue'
import { ElMessage, ElMessageBox } from 'element-plus'
import { getScorePage, addScore, updateScore, deleteScore, getStatistics } from '../api/score'

const tableData = ref([])
const total = ref(0)
const pageNum = ref(1)
const pageSize = ref(5)
const keyword = ref('')
const dialogVisible = ref(false)
const statisticsVisible = ref(false)
const dialogTitle = ref('录入成绩')
const isEdit = ref(false)
const formRef = ref(null)

const statistics = reactive({
  count: 0,
  avg: 0,
  max: 0,
  min: 0,
  passRate: 0
})

const formData = reactive({
  scoreId: null,
  studentId: '',
  courseId: null,
  score: null
})

const rules = {
  studentId: [{ required: true, message: '请输入学号', trigger: 'blur' }],
  courseId: [{ required: true, message: '请输入课程编号', trigger: 'blur' }],
  score: [
    { required: true, message: '请输入分数', trigger: 'blur' },
    { type: 'number', min: 0, max: 100, message: '分数必须在0-100之间', trigger: 'blur' }
  ]
}

const getScoreTag = (score) => {
  if (score >= 90) return 'success'
  if (score >= 60) return 'warning'
  return 'danger'
}

const loadData = async () => {
  try {
    const res = await getScorePage(pageNum.value, pageSize.value, keyword.value)
    tableData.value = res.data.records || []
    total.value = res.data.total || 0
  } catch (error) {
    ElMessage.error('加载数据失败')
  }
}

const openAddDialog = () => {
  isEdit.value = false
  dialogTitle.value = '录入成绩'
  formData.scoreId = null
  formData.studentId = ''
  formData.courseId = null
  formData.score = null
  dialogVisible.value = true
}

const openEditDialog = (row) => {
  isEdit.value = true
  dialogTitle.value = '编辑成绩'
  Object.assign(formData, row)
  dialogVisible.value = true
}

const submitForm = async () => {
  await formRef.value.validate()
  try {
    if (isEdit.value) {
      await updateScore(formData)
      ElMessage.success('更新成功')
    } else {
      await addScore(formData)
      ElMessage.success('录入成功')
    }
    dialogVisible.value = false
    loadData()
  } catch (error) {
    ElMessage.error('操作失败，请检查输入')
  }
}

const handleDelete = (scoreId) => {
  ElMessageBox.confirm('确认删除该成绩吗？', '提示', { type: 'warning' })
    .then(async () => {
      await deleteScore(scoreId)
      ElMessage.success('删除成功')
      loadData()
    })
    .catch(() => {})
}

const openStatistics = async () => {
  // 默认统计课程ID为1，实际使用时可以添加课程选择器
  const courseId = 1
  try {
    const res = await getStatistics(courseId)
    Object.assign(statistics, res.data)
    statisticsVisible.value = true
  } catch (error) {
    ElMessage.error('加载统计数据失败')
  }
}

onMounted(() => {
  loadData()
})
</script>