<template>
  <div id="app">
    <!-- 顶部导航栏 -->
    <el-container>
      <el-header style="background-color: #409EFF; padding: 0; height: 60px; display: flex; align-items: center; padding: 0 20px;">
        <div style="display: flex; align-items: center; width: 100%;">
          <span style="color: white; font-size: 20px; font-weight: bold; margin-right: 40px;">
            📚 学生成绩管理系统
          </span>
          <el-menu
            :default-active="activeIndex"
            mode="horizontal"
            background-color="#409EFF"
            text-color="#ffffff"
            active-text-color="#ffd04b"
            style="border-bottom: none; flex: 1;"
            @select="handleSelect"
          >
            <el-menu-item index="/students">学生管理</el-menu-item>
            <el-menu-item index="/courses">课程管理</el-menu-item>
            <el-menu-item index="/scores">成绩管理</el-menu-item>
            <el-menu-item index="/statistics">成绩统计</el-menu-item>
          </el-menu>
        </div>
      </el-header>
      <el-main style="background-color: #f0f2f5; padding: 20px; min-height: calc(100vh - 60px);">
        <router-view />
      </el-main>
    </el-container>
  </div>
</template>

<script setup>
import { ref, watch } from 'vue'
import { useRoute, useRouter } from 'vue-router'

const route = useRoute()
const router = useRouter()
const activeIndex = ref('/students')

// 监听路由变化，更新高亮菜单
watch(
  () => route.path,
  (newPath) => {
    activeIndex.value = newPath
  },
  { immediate: true }
)

const handleSelect = (index) => {
  router.push(index)
}
</script>

<style>
* {
  margin: 0;
  padding: 0;
  box-sizing: border-box;
}

body {
  font-family: 'Microsoft YaHei', sans-serif;
  background-color: #f0f2f5;
}

#app {
  width: 100%;
  min-height: 100vh;
}

.el-header {
  --el-header-padding: 0 20px;
}

.el-menu--horizontal > .el-menu-item {
  height: 60px;
  line-height: 60px;
}

.el-main {
  padding: 20px;
}

/* 表格样式优化 */
.el-table {
  border-radius: 8px;
  overflow: hidden;
}

.el-card {
  border-radius: 8px;
}

/* 统计卡片数字样式 */
.el-statistic .el-statistic__number {
  font-size: 28px;
  font-weight: bold;
}

/* 分页组件居中 */
.el-pagination {
  justify-content: flex-end;
  margin-top: 20px;
}
</style>
