# CATE：用 Docker 启动和分享

这份说明只要求安装 Docker（Windows/macOS 使用 Docker Desktop），不需要在电脑上安装 Java、Maven、Node.js 或 MySQL。先确认 `docker version` 同时显示 Client 和 Server。

## 1. 快速体验：直接运行已发布镜像（推荐）

安装并启动 Docker 后执行：

```bash
git clone https://github.com/Candy-Fans/CATE.git
cd CATE
docker compose -f compose.release.yaml up --pull always --detach --wait
```

不想下载源码时，也可以只下载根目录的 `compose.release.yaml`，放入空目录后执行上面的最后一条命令。
发布配置默认使用 `candyfans/cate-db:1.0.0`、`candyfans/cate-server:1.0.0`、`candyfans/cate-web:1.0.0`；首次只需下载镜像，不需要编译 Java 或前端。完成后打开：

| 页面 | 地址 |
| --- | --- |
| 用户端 | http://localhost:8081 |
| 管理端 | http://localhost:8080 |

演示管理员：`demo-admin` / `123456`。普通用户在用户端自行注册。

默认即可运行，不需要复制 `.env`，不需要手动建库或导入 SQL。Compose 会启动三个服务：

- `mysql`：MySQL 8.0，第一次启动自动导入镜像中打包的 `cate.sql`。
- `backend`：Java 8 / Spring Boot 后端，数据库使用 Compose 内部地址 `mysql`。
- `web`：Nginx 同时提供两个前端，并把同源的 `/api/` 请求转发给后端。

浏览器通过同源地址访问接口和图片，换成服务器 IP 时无需修改前端代码。数据库和后端不向宿主机发布端口，不会占用本机的 3306 或 8888。

## 2. 修改源码后构建运行

将本次 Docker 文件和前端配置改动提交、推送到自己的仓库。接收者只需安装 Docker，然后执行：

```bash
git clone https://github.com/Candy-Fans/CATE.git
cd CATE
docker compose up --build --detach --wait
```

这条路线中，每个人首次运行都会构建镜像；后续启动通常直接复用本机镜像和缓存。

## 3. 发布新版本镜像与同步 GitHub

当前已发布版本为 `1.0.0`，镜像前缀为 `candyfans/cate`。镜像上传到 Docker Hub 不会自动更新 GitHub，需要另外提交源码、Docker 配置和部署说明。

以后修改程序并发布新版本时，例如 `1.0.1`，在 CATE 根目录打开 PowerShell：

```powershell
$env:CATE_IMAGE_PREFIX = 'candyfans/cate'
$env:CATE_IMAGE_TAG = '1.0.1'
docker login
docker compose build
docker compose push
```

这会发布三个镜像：`candyfans/cate-db:1.0.1`、`candyfans/cate-server:1.0.1`、`candyfans/cate-web:1.0.1`。数据库结构、演示数据和初始图片都在镜像里。若希望别人无需登录即可下载，三个 Docker Hub 镜像仓库均需设为 Public。

发布后，将 `compose.release.yaml` 中三处 `CATE_IMAGE_TAG` 的默认值更新为新版本，并同步 README 中的版本说明，再把这些文件及相应源码提交到 GitHub。仅修改说明或 Compose 文件时，无需重新构建、推送镜像。可在 GitHub 创建同版本 Release，并附上 `compose.release.yaml`，方便别人直接下载。

已有部署要切换到其他已发布版本，可在 `compose.release.yaml` 所在目录创建或修改 `.env`（不应提交到 Git）：

```dotenv
CATE_IMAGE_TAG=1.0.1
```

然后执行 `docker compose -f compose.release.yaml up --pull always --detach --wait`。MySQL 初始化 SQL 只对空数据卷生效，涉及表结构变化时还需要准备数据库迁移，不能仅靠换数据库镜像升级已有数据。

注意：终端中的 `CATE_IMAGE_PREFIX`、`CATE_IMAGE_TAG` 优先于 `.env` 和默认值。如果之前设置过这两个变量，需确认它们指向你要运行的镜像。示例配置 `.env.docker.example` 默认注释这两项，以免覆盖发布配置。

当前镜像构建验证以 Linux/amd64 为准（包括本机 Windows Docker Desktop 的 Linux 引擎）；其他 CPU 架构需要单独构建、验证，或使用 Docker 支持的 amd64 模拟运行。

## 4. 不方便用镜像仓库：发离线包

这条路线使用源码构建配置 `compose.yaml`。先确保 `CATE_IMAGE_PREFIX=cate`、`CATE_IMAGE_TAG=local`（或未设置这两个变量），再构建、导出三个镜像：

```bash
docker compose build
docker image save --output cate-images.tar cate-db:local cate-server:local cate-web:local
```

把 `cate-images.tar` 和根目录的 `compose.yaml` 一起发给接收者。接收者在这两个文件所在目录执行：

```bash
docker image load --input cate-images.tar
docker compose up --no-build --pull never --detach --wait
```

这条路线使用默认 `CATE_IMAGE_PREFIX=cate`、`CATE_IMAGE_TAG=local`；若终端或 `.env` 中设过其他镜像前缀、版本，需先恢复这两个值。

## 5. 日常操作与数据

以下命令对应推荐的镜像部署方式。源码构建用户使用 `compose.yaml`，可省略 `-f compose.release.yaml`。

```bash
# 看运行和健康状态
docker compose -f compose.release.yaml ps

# 查看后端日志（Ctrl+C 只退出看日志）
docker compose -f compose.release.yaml logs --follow backend

# 停止，保留数据
docker compose -f compose.release.yaml stop

# 再启动已经存在的容器
docker compose -f compose.release.yaml start

# 删除容器和网络，保留数据卷
docker compose -f compose.release.yaml down
```

数据库、菜品图片、头像、上传的 food 文件和 mv 文件分别存放在命名数据卷中，`stop`、`down` 和重新构建镜像不会删除它们。数据库 SQL 仅在空数据库卷首次初始化时导入，不会每次启动重置数据。

**只有明确要清空全部容器数据并重新初始化时，才使用 `docker compose -f compose.release.yaml down --volumes`。** 此命令会删除数据库和上传文件，无法通过重新启动恢复。

已有数据卷时，更改环境变量中的数据库密码不会自动修改 MySQL 内已经创建的账号密码；应在数据库中修改，或在确认不需要旧数据后重新初始化。

## 6. 改端口、让局域网访问

可选：将根目录 `.env.docker.example` 复制为 `.env`，再修改配置。不要把原生后端使用的 `.env.example` 直接用作 Docker 配置。

例如本机端口被占用，可在 `.env` 中修改：

```dotenv
CATE_MANAGE_PORT=18080
CATE_CLIENT_PORT=18081
```

再次执行 `docker compose -f compose.release.yaml up --detach --wait` 后访问对应新端口。

默认只绑定 `127.0.0.1`。需要让同一局域网的其他设备访问时，在 `.env` 中设置：

```dotenv
CATE_BIND_ADDRESS=0.0.0.0
```

重新执行启动命令，并在系统防火墙中放行前端端口。其他设备访问 `http://这台电脑的局域网IP:8081` 和 `http://这台电脑的局域网IP:8080`。

默认演示账号、数据库密码、监控密码和加密密钥用于本机体验。镜像分享不等于安全的公网部署；正式开放服务前，按 README 的数据与安全说明更换演示凭据、配置 HTTPS 并处理限流等要求。更改加密密钥后，旧密钥加密的演示管理员密码也需同步迁移，不能只改密钥。

## 7. 本机代理导致构建失败

Git 代理、Docker Desktop 代理和构建命令的代理设置是不同环节。若错误发生在获取 Docker Hub 的 `anonymous token`，可在当前 PowerShell 为构建命令设置本机实际代理端口：

```powershell
# 7897 是当前开发机验证可用的端口；其他电脑必须按实际代理设置修改。
$env:HTTP_PROXY = 'http://127.0.0.1:7897'
$env:HTTPS_PROXY = 'http://127.0.0.1:7897'
docker compose up --build --detach --wait
```

这些变量只影响当前终端及其启动的进程。未使用本地代理的电脑不需要执行这两行。容器内部的 npm/Maven 网络如果报错，应单独检查容器网络，不能把容器里的 `127.0.0.1` 当成 Windows 主机地址。
