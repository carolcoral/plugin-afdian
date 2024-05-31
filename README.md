# plugin-afdian


| ![xindu.site](https://img.shields.io/badge/Blog-新·都在-green?style=flat&label=Blog&labelColor=blue&link=https%3A%2F%2Fblog.xindu.site%2F) | ![plugin-afdian](https://img.shields.io/badge/Plugin-Afdian-blue?style=flat&label=Plugin&labelColor=FB7A16&link=https%3A%2F%2Fgithub.com%2Fcarolcoral%2Fplugin-afdian) | ![halo](https://img.shields.io/badge/Frame-Halo-F8E60E?style=flat&label=Frame&labelColor=C616FB&link=https%3A%2F%2Fwww.halo.run%2F) |
|:----------------------------------------------------------------------------------------------------------------------------------------|:-----------------------------------------------------------------------------------------------------------------------------------------------------------------------|:----------------------------------------------------------------------------------------------------------------------------------|

> Halo 2.0 爱发电插件

> 预览效果: [新 · 都在](https://blog.xindu.site/zanzhu)

> 代码仓库: [plugin-afdian](https://github.com/carolcoral/plugin-afdian)

> 插件自带多款赞赏展示页面

![theme1](https://redirect.cnkj.site:8099/shudaosan/2024/66568d582726b.webp?type=blog)
![theme3](./theme3.png)

## 使用方式
1. 启动插件
2. 在插件配置中填写自己的userId和token
    * user_id: 登录爱发电网站后访问[开发者页面](https://afdian.net/dashboard/dev)，复制`开发者`下方的`user_id`值
    * token: 登录爱发电网站后访问[开发者页面](https://afdian.net/dashboard/dev)，在页面最下方`API Token（用来主动请求API用）`点击`生成`，然后复制生成的token即可

* 主题开发者可以使用下面的接口完成定制化开发
* 使用地址 `/afdian` 可访问插件默认展示页面

## 文档
[https://blog.xindu.site/docs/plugin-afdian](https://blog.xindu.site/docs/plugin-afdian)

## 开发环境

插件开发的详细文档请查阅：<https://docs.halo.run/developer-guide/plugin/introduction>

所需环境：

1. Java 17
2. Node 18
3. pnpm 8
4. Docker (可选)

克隆项目：

```bash
git clone git@github.com:carolcoral/plugin-afdian.git

# 或者当你 fork 之后

git clone git@github.com:{your_github_id}/plugin-afdian.git
```

```bash
cd path/to/plugin-afdian
```

### 运行方式 1（推荐）

> 此方式需要本地安装 Docker

```bash
# macOS / Linux
./gradlew pnpmInstall

# Windows
./gradlew.bat pnpmInstall
```

```bash
# macOS / Linux
./gradlew haloServer

# Windows
./gradlew.bat haloServer
```

执行此命令后，会自动创建一个 Halo 的 Docker 容器并加载当前的插件，更多文档可查阅：<https://docs.halo.run/developer-guide/plugin/basics/devtools>

### 运行方式 2

> 此方式需要使用源码运行 Halo

编译插件：

```bash
# macOS / Linux
./gradlew build

# Windows
./gradlew.bat build
```

修改 Halo 配置文件：

```yaml
halo:
  plugin:
    runtime-mode: development
    fixedPluginPath:
      - "/path/to/plugin-afdian"
```

最后重启 Halo 项目即可。