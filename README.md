# CATE 校园美食点评系统

CATE 是一个前后端分离的校园美食点评学习项目，包含用户端、管理端和 Spring Boot 后端。用户可以浏览与搜索菜品和窗口，注册登录后进行收藏、评论和评分；管理员可以维护菜品、窗口、合集及用户内容。

## 功能

- 用户注册、登录和个人资料维护
- 菜品、食堂窗口与美食合集浏览
- 搜索、收藏、评论和评分
- 管理端的菜品、窗口、合集、用户及评论管理
- Druid 数据源监控和 Sa-Token 登录鉴权

## 技术栈

| 部分 | 技术 |
| --- | --- |
| 后端 | Java 8、Spring Boot 2.3.7、MyBatis、Maven |
| 数据库 | MySQL 8.0、Druid |
| 鉴权 | Sa-Token 1.34 |
| 前端 | Vue 2、Vue Router、Vuex、Element UI、Webpack |
| 推荐 Node.js | 14.15.1（项目使用旧版 `node-sass`，不建议直接升级） |

## 项目结构

```text
CATE/
├── cate-server/          # Spring Boot 后端，端口 8888
├── front/
│   ├── cate-client/      # 用户端，端口 8081
│   └── cate-manage/      # 管理端，端口 8080
├── cate.sql              # 已脱敏的数据库结构和演示数据
└── .env.example          # 所需环境变量示例，不包含真实密码
```

## 界面截图

![CATE 用户端首页](docs/images/client-home.png)

## 本机环境

推荐使用以下版本：

- JDK 8
- Maven 3.9.x
- MySQL 8.0.x
- Node.js 14.15.1
- IDEA（后端）和 VS Code（前端）

当前电脑上的开发工具位于 `D:\SoftWare\Develop`。其他电脑不必使用相同路径，只要版本兼容并已加入 `PATH` 即可。

## 首次运行

### 1. 导入数据库

先在一个终端启动 MySQL，并保持该终端运行：

```powershell
& 'D:\SoftWare\Develop\MySQL\mysql-8.0.31-winx64\bin\mysqld.exe' `
  '--basedir=D:\SoftWare\Develop\MySQL\mysql-8.0.31-winx64' `
  '--datadir=D:\SoftWare\Develop\MySQL\data' `
  '--console'
```

再打开一个终端进入 MySQL：

```powershell
& 'D:\SoftWare\Develop\MySQL\mysql-8.0.31-winx64\bin\mysql.exe' -u root -p
```

输入本机数据库密码后，在 MySQL 中执行：

```sql
CREATE DATABASE IF NOT EXISTS cate DEFAULT CHARACTER SET utf8mb4;
USE cate;
SOURCE D:/WorkSpace/CATE/cate.sql;
```

公开版 SQL 已删除原有用户、手机号、邮箱、评论、收藏和评分，只保留业务展示数据与一个虚构管理员。

### 2. 设置本机环境变量

`.env.example` 只是变量清单，Spring Boot 不会自动读取它。每次新开 PowerShell 后，在同一个窗口中设置：

```powershell
$env:JAVA_HOME = 'D:\SoftWare\Develop\JDK\JDK8'
$env:Path = "$env:JAVA_HOME\bin;D:\SoftWare\Develop\Maven\apache-maven-3.9.11\bin;D:\SoftWare\Develop\nvm\v14.15.1;$env:Path"
$env:CATE_DB_USERNAME = 'root'
$env:CATE_DB_PASSWORD = '填写你自己的 MySQL 密码'
$env:CATE_DRUID_USERNAME = 'admin'
$env:CATE_DRUID_PASSWORD = '填写你自己的监控页密码'
$env:CATE_CIPHER_KEY = '0123456789abcdef0123456789abcdef'
```

最后一项是与公开演示管理员匹配的演示密钥。它已经公开，只适合本地体验；真正部署时应更换密钥，并重新创建管理员密码。以上是五个必填的 `CATE_...` 变量；只有数据库地址不是默认的 `localhost:3306/cate` 时，才需另外设置 `.env.example` 中的 `CATE_DB_URL`。

### 3. 启动后端

```powershell
cd D:\WorkSpace\CATE\cate-server
mvn spring-boot:run
```

后端地址：`http://localhost:8888`

在 IDEA 中可以打开 `cate-server/pom.xml`，将 Project SDK 设为 JDK 8、Maven Home 设为本机 Maven 目录。编辑 `CateApplication` 的运行配置，在 Environment variables 中填写上面的六个 `CATE_...` 变量，然后运行 `CateApplication.main()`。

### 4. 启动两个前端

分别打开两个新的 VS Code 终端：

```powershell
$env:Path = "D:\SoftWare\Develop\nvm\v14.15.1;$env:Path"
cd D:\WorkSpace\CATE\front\cate-manage
npm install
npm run dev
```

```powershell
$env:Path = "D:\SoftWare\Develop\nvm\v14.15.1;$env:Path"
cd D:\WorkSpace\CATE\front\cate-client
npm install
npm run dev
```

- 管理端：`http://localhost:8080`
- 用户端：`http://localhost:8081`

管理端本地演示账号：`demo-admin`，密码：`123456`。用户端没有预置个人账号，请通过注册页面创建。

已安装过依赖时不必每次执行 `npm install`；日常启动只需执行 `npm run dev`。停止前后端时，在对应终端按 `Ctrl+C`。停止 MySQL 时，可以在另一个终端执行：

```powershell
& 'D:\SoftWare\Develop\MySQL\mysql-8.0.31-winx64\bin\mysqladmin.exe' -u root -p shutdown
```

## 构建检查

```powershell
cd D:\WorkSpace\CATE\cate-server
mvn clean package

cd D:\WorkSpace\CATE\front\cate-manage
npm run build

cd D:\WorkSpace\CATE\front\cate-client
npm run build
```

## GitHub 发布步骤

本地仓库使用 `main` 分支。首次发布前先在 GitHub 新建一个空仓库，不要勾选自动创建 README、`.gitignore` 或 License，然后执行：

```powershell
cd D:\WorkSpace\CATE
git config user.name "你的 GitHub 显示名称"
git config user.email "你的 GitHub 邮箱"
git add .
git status
git commit -m "chore: initial public version"
git remote add origin https://github.com/你的用户名/你的仓库名.git
git push -u origin main
```

以后每次完成一小组修改：

```powershell
git status
git diff
git add .
git commit -m "feat: 简短说明这次做了什么"
git push
```

提交前要先看 `git status` 和 `git diff`，确认没有数据库密码、密钥、个人资料、构建目录或无关大文件。

## 数据与安全说明

- 数据库密码、Druid 账号密码及应用加密密钥由环境变量提供，不应提交真实值。
- `.env` 和 `.env.*` 已被 Git 忽略，`.env.example` 只保存占位示例。
- `cate.sql` 中原有账号及用户行为数据已清除。
- `cate-server/avatorImages` 中的本机用户头像已被 Git 忽略，只保留空目录占位文件。
- 演示账号、演示密钥和示例数据不能用于生产环境。

## 来源与许可证

本仓库用于个人学习、实践和持续改进。现有包名、代码署名等信息表明项目可能基于教程或其他示例项目，但原始来源和授权条款目前尚未核实。因此，本仓库不声明全部代码均为原创，也暂不附加开源许可证。

在公开宣传前，应补充原教程或上游仓库链接、保留原作者要求的署名，并在项目介绍中明确说明自己完成的修改。你可以将它描述为“我学习、搭建并持续维护的项目”，但在来源确认前不宜描述为“全部从零独立原创”。
