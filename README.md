# CATE 校园美食点评系统

CATE 是一个前后端分离的校园美食点评学习项目，由 Spring Boot 后端、用户端和管理端组成。用户可以浏览、搜索、收藏、评论和评分，管理员可以维护菜品、食堂窗口、美食合集及用户内容。

![CATE 用户端首页](docs/images/client-home.png)

## 功能概览

- 用户注册、登录和个人资料维护（注册含图片验证码）
- 菜品、食堂窗口与美食合集浏览
- 搜索、收藏、评论和评分
- 菜品、窗口、合集、用户及评论管理

## 注册验证码

注册时需填写 5 位图片验证码，不区分大小写，有效期为 5 分钟，点击图片可刷新。
验证码与当前浏览器会话绑定，答案只保存在后端；每次提交后立即失效，失败时前端会自动刷新。
管理端“添加新用户”使用同样的验证流程。无需短信服务、额外密钥或数据库变更。
这项措施用于提高自动化注册成本，无法保证拦住所有机器人；正式公网部署时应结合请求限流。

仅验证验证码功能（不需要数据库）可在 `cate-server` 目录执行：

```bash
mvn -Dtest=RegistrationCaptchaServiceTest,RegistrationCaptchaControllerTest test
```

用户端相关测试可在 `front/cate-client` 目录执行：

```bash
npm run unit -- --runInBand test/unit/specs/SignUp.spec.js
```

## 技术栈

| 模块 | 技术 |
| --- | --- |
| 后端 | Java 8、Spring Boot 2.3.7、MyBatis、Maven |
| 数据库 | MySQL 8.0、Druid |
| 鉴权 | Sa-Token 1.34 |
| 前端 | Vue 2、Vue Router、Vuex、Element UI、Webpack 3 |

## 目录结构

```text
CATE/
├── cate-server/          # Spring Boot 后端
├── front/
│   ├── cate-client/      # 用户端
│   └── cate-manage/      # 管理端
├── docs/images/          # README 截图
├── cate.sql              # 数据库结构和演示数据
└── .env.example          # 后端环境变量示例
```

## 运行环境

建议使用下列版本。前端依赖较旧，特别是 `node-sass 4.14.1`，请优先使用 Node.js 14，避免直接使用最新版 Node.js。

| 软件 | 建议版本 |
| --- | --- |
| Git | 任意较新版本 |
| JDK | 8 |
| Maven | 3.6 及以上 |
| MySQL | 8.0 |
| Node.js | 14.15.1 |
| npm | Node.js 14 自带版本（通常为 npm 6） |

安装完成后，可以先确认命令均可用：

```bash
git --version
java -version
mvn -version
mysql --version
node -v
npm -v
```

如果某条命令提示“不是内部或外部命令”或“command not found”，请先把对应软件的 `bin` 目录加入系统 `PATH`。

## 快速开始

### 1. 克隆项目

```bash
git clone https://github.com/Candy-Fans/CATE.git
cd CATE
```

后续数据库导入命令默认在项目根目录 `CATE` 中执行。

### 2. 创建并导入数据库

先启动本机 MySQL，然后在项目根目录连接数据库：

```bash
mysql -u root -p
```

输入 MySQL 密码后，在 MySQL 命令行中依次执行：

```sql
CREATE DATABASE IF NOT EXISTS cate
  DEFAULT CHARACTER SET utf8mb4
  COLLATE utf8mb4_general_ci;

USE cate;
SOURCE cate.sql;
EXIT;
```

导入成功后应存在 `admin`、`consumer`、`food`、`food_window` 等数据表。

> `cate.sql` 会重建同名数据表。请勿直接导入到存有重要数据的数据库中。

### 3. 配置后端环境变量

后端不会自动读取 `.env.example`，需要在启动后端的同一个终端或 IDEA 运行配置中设置环境变量。

必须配置以下五项：

| 变量 | 示例值 | 说明 |
| --- | --- | --- |
| `CATE_DB_USERNAME` | `root` | MySQL 用户名 |
| `CATE_DB_PASSWORD` | `your_mysql_password` | MySQL 密码 |
| `CATE_DRUID_USERNAME` | `admin` | Druid 监控页用户名 |
| `CATE_DRUID_PASSWORD` | `your_druid_password` | Druid 监控页密码 |
| `CATE_CIPHER_KEY` | `0123456789abcdef0123456789abcdef` | 本地演示数据使用的加密密钥 |

默认数据库地址为 `localhost:3306/cate`。只有数据库地址不同时，才需要额外设置 `CATE_DB_URL`，完整格式可参考 [.env.example](.env.example)。

#### Windows PowerShell

```powershell
$env:CATE_DB_USERNAME = 'root'
$env:CATE_DB_PASSWORD = '替换为你的 MySQL 密码'
$env:CATE_DRUID_USERNAME = 'admin'
$env:CATE_DRUID_PASSWORD = '设置一个 Druid 监控页密码'
$env:CATE_CIPHER_KEY = '0123456789abcdef0123456789abcdef'
```

#### Windows CMD

```cmd
set CATE_DB_USERNAME=root
set CATE_DB_PASSWORD=替换为你的 MySQL 密码
set CATE_DRUID_USERNAME=admin
set CATE_DRUID_PASSWORD=设置一个 Druid 监控页密码
set CATE_CIPHER_KEY=0123456789abcdef0123456789abcdef
```

#### macOS / Linux

```bash
export CATE_DB_USERNAME=root
export CATE_DB_PASSWORD='替换为你的 MySQL 密码'
export CATE_DRUID_USERNAME=admin
export CATE_DRUID_PASSWORD='设置一个 Druid 监控页密码'
export CATE_CIPHER_KEY='0123456789abcdef0123456789abcdef'
```

这些命令只对当前终端生效，关闭终端后需要重新设置。示例 `CATE_CIPHER_KEY` 用于匹配 SQL 中的演示管理员密码，仅适合本地运行；自行部署时应更换密钥和所有演示密码。

### 4. 启动后端

在已经设置环境变量的终端中执行：

```bash
cd cate-server
mvn spring-boot:run
```

出现 Spring Boot 启动完成日志后，后端运行在：

- API：<http://localhost:8888>
- Druid 监控：<http://localhost:8888/druid/>

首次运行时 Maven 会下载依赖，耗时取决于网络状况。

### 5. 启动管理端

新开一个终端，从项目根目录执行：

```bash
cd front/cate-manage
npm install
npm run dev
```

管理端地址：<http://localhost:8080>

本地演示管理员：

- 用户名：`demo-admin`
- 密码：`123456`

### 6. 启动用户端

再新开一个终端，从项目根目录执行：

```bash
cd front/cate-client
npm install
npm run dev
```

用户端地址：<http://localhost:8081>

数据库没有预置普通用户，请在用户端注册一个本地测试账号。

`npm install` 只需在首次运行或依赖发生变化时执行。日常开发通常只需运行 `npm run dev`。

## 使用 IDEA 和 VS Code 运行

### IDEA 启动后端

1. 在 IDEA 中打开 `cate-server/pom.xml`，选择“作为项目打开”。
2. 将 Project SDK 设置为 JDK 8，等待 Maven 依赖下载完成。
3. 找到 `com.javaclimb.cate.CateApplication`。
4. 打开该启动类的 Run Configuration，在 Environment variables 中添加前文的五个 `CATE_...` 变量。
5. 运行 `CateApplication.main()`。

如果 IDEA 已经使用 Maven 成功导入项目，通常不需要再手动执行 `mvn install`。

### VS Code 启动前端

分别用 VS Code 打开 `front/cate-manage` 和 `front/cate-client`，在各自的集成终端执行：

```bash
npm install
npm run dev
```

确认 VS Code 终端中的 `node -v` 为 `v14.x`，否则旧版 `node-sass` 可能安装失败。

## 服务地址

| 服务 | 地址 |
| --- | --- |
| 后端 API | <http://localhost:8888> |
| 管理端 | <http://localhost:8080> |
| 用户端 | <http://localhost:8081> |
| Druid 监控 | <http://localhost:8888/druid/> |
| MySQL | `localhost:3306/cate` |

## 日常启动顺序

已经完成首次安装后，按以下顺序启动即可：

1. 启动 MySQL。
2. 设置后端所需环境变量并启动 `cate-server`。
3. 在 `front/cate-manage` 中运行 `npm run dev`。
4. 在 `front/cate-client` 中运行 `npm run dev`。

停止前端或后端时，在对应终端按 `Ctrl+C`。MySQL 请使用本机安装方式提供的服务管理命令或管理工具停止。

## 构建项目

后端：

```bash
cd cate-server
mvn clean package
```

管理端：

```bash
cd front/cate-manage
npm run build
```

用户端：

```bash
cd front/cate-client
npm run build
```

## 常见问题

### `mysql`、`mvn`、`java`、`node` 或 `npm` 无法识别

对应软件没有安装，或者可执行文件目录没有加入 `PATH`。修复后重新打开终端，再运行版本检查命令。

### 后端提示缺少 `CATE_...` 配置

环境变量必须和后端在同一个终端会话中设置。使用 IDEA 时，应将变量填写在 `CateApplication` 的 Run Configuration 中。

### 后端无法连接 MySQL

依次确认：

1. MySQL 已启动并监听 `3306` 端口。
2. `cate` 数据库已经创建并导入 `cate.sql`。
3. `CATE_DB_USERNAME` 和 `CATE_DB_PASSWORD` 与本机 MySQL 一致。
4. 非默认数据库地址已经通过 `CATE_DB_URL` 配置。

### `node-sass` 安装或编译失败

先确认使用 Node.js 14，再删除当前前端目录中的 `node_modules`，重新执行 `npm install`。不要用 Node.js 18、20 或更高版本直接安装本项目的旧版依赖。

### 端口被占用

默认端口为后端 `8888`、管理端 `8080`、用户端 `8081`。请关闭占用端口的进程，或修改对应模块的端口配置后再启动。

## 数据与安全说明

- 仓库不包含真实数据库密码、Druid 密码或私有加密密钥。
- `.env` 和 `.env.*` 已被 Git 忽略，`.env.example` 只保存变量示例。
- `cate.sql` 已移除原有用户、手机号、邮箱、评论、收藏和评分记录。
- 演示账号、演示密码和示例密钥只用于本地体验，不可直接用于生产环境。
- 用户上传的本机头像文件不会提交到 Git。

## 来源与许可证

本仓库用于个人学习、实践和持续改进。项目部分内容基于网络教程和其他示例代码，仅供个人参考学习。
