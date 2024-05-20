# plugin-afdian
> Halo 2.0 爱发电插件

> 提供API接口信息返回爱发电赞助信息

> 预览效果: [新 · 都在](https://blog.xindu.site/)

## TODO
- [ ] 获取用户全部赞助方案并展示
- [ ] 获取用户全部商品内容并展示
- [ ] 提供独立的爱发电赞助展示页面

## 使用
1. 启动插件
2. 在插件配置中填写自己的userId和token
    * user_id: 登录爱发电网站后访问[开发者页面](https://afdian.net/dashboard/dev)，复制`开发者`下方的`user_id`值
    * token: 登录爱发电网站后访问[开发者页面](https://afdian.net/dashboard/dev)，在页面最下方`API Token（用来主动请求API用）`点击`生成`，然后复制生成的token即可
3. 主题开发者可以使用下面的接口完成定制化开发

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

## 接口信息

### 查询赞助者
* api: `/apis/api.plugin.halo.run/v1alpha1/plugins/plugin-afdian/afdian/getSponsorList`

## Finder

### 分页查询赞助者
* afdianFinder.listSponsor(String pageNumber)

示例: 
```html
<div th:text="${afdianFinder.listSponsor(1)}"></div>
```

### 查询全部赞助者
* afdianFinder.listAllSponsor()

示例:
```html
<div th:text="${afdianFinder.listAllSponsor()}"></div>
```

## 返回报文JSON
```json
{
    "ec": 200,
    "em": "sponsor",
    "data": {
        "total_count": 2,
        "total_page": 1,
        "list": [
            {
                "sponsor_plans": [
                    {
                        "can_ali_agreement": 0,
                        "plan_id": "fcb1d38cfad811eeb2f75254001e7c00",
                        "rank": 1,
                        "user_id": "333022d85d9f11eebef852540025c377",
                        "status": 1,
                        "name": "好看的个人主页",
                        "pic": "https://pic1.afdiancdn.com/user/333022d85d9f11eebef852540025c377/common/025ec22852ae79ab9eae9ccc248530cd_w3338_h1798_s7022.png",
                        "desc": "<p>好看的个人主页，支持PC、平板、移动端全部兼容。</p><p>通过配置文件简单配置即可完成使用。</p><p>本商品为代码类虚拟商品，一经售出，不退不换！</p><p>预览地址：https://xindu.site/</p><p><img src=\"https://pic1.afdiancdn.com/user/333022d85d9f11eebef852540025c377/common/025ec22852ae79ab9eae9ccc248530cd_w3338_h1798_s7022.png?imageView2/2/w/1280\" class=\"fr-fic fr-dib\" data-ec=\"200\" data-em=\"上传成功\" data-data=\"[object Object]\"></p><p><img src=\"https://pic1.afdiancdn.com/user/333022d85d9f11eebef852540025c377/common/8b9c138d761f1a09f34e084943c593f6_w1944_h1774_s1800.png?imageView2/2/w/1280\" class=\"fr-fic fr-dib\" data-ec=\"200\" data-em=\"上传成功\" data-data=\"[object Object]\"></p><p><img src=\"https://pic1.afdiancdn.com/user/333022d85d9f11eebef852540025c377/common/ae005739a78276a70947f27d32a76c86_w970_h1768_s1215.png?imageView2/2/w/1280\" class=\"fr-fic fr-dib\" data-ec=\"200\" data-em=\"上传成功\" data-data=\"[object Object]\"></p>",
                        "price": "12.00",
                        "update_time": 1714101669,
                        "timing": {
                            "timing_on": 0,
                            "timing_off": 0
                        },
                        "pay_month": 1,
                        "show_price": "12.00",
                        "show_price_after_adjust": "",
                        "has_coupon": 0,
                        "coupon": [],
                        "favorable_price": -1,
                        "independent": 0,
                        "permanent": 0,
                        "can_buy_hide": 1,
                        "need_address": 0,
                        "product_type": 1,
                        "sale_limit_count": -1,
                        "need_invite_code": false,
                        "bundle_stock": 0,
                        "bundle_sku_select_count": 0,
                        "config": {},
                        "has_plan_config": 0,
                        "shipping_fee_info": {
                            "id": 11,
                            "name": "全国包邮",
                            "user_id": "system",
                            "status": 1,
                            "template_type": 1,
                            "fee_type": 1,
                            "base_fee": "0.00",
                            "base_fee_count": 1,
                            "addup_fee": "0.00",
                            "addup_count": 1,
                            "create_time": 0,
                            "update_time": 0
                        },
                        "expire_time": 2114352000,
                        "sku_processed": [
                            {
                                "sku_id": "fcbe5774fad811eeaaff5254001e7c00",
                                "price": "8.80",
                                "count": 1,
                                "name": "好看的个人主页",
                                "album_id": "",
                                "pic": "",
                                "stock": "",
                                "post_id": ""
                            }
                        ],
                        "rankType": 30
                    }
                ],
                "current_plan": {
                    "can_ali_agreement": 0,
                    "plan_id": "fcb1d38cfad811eeb2f75254001e7c00",
                    "rank": 1,
                    "user_id": "333022d85d9f11eebef852540025c377",
                    "status": 1,
                    "name": "好看的个人主页",
                    "pic": "https://pic1.afdiancdn.com/user/333022d85d9f11eebef852540025c377/common/025ec22852ae79ab9eae9ccc248530cd_w3338_h1798_s7022.png",
                    "desc": "<p>好看的个人主页，支持PC、平板、移动端全部兼容。</p><p>通过配置文件简单配置即可完成使用。</p><p>本商品为代码类虚拟商品，一经售出，不退不换！</p><p>预览地址：https://xindu.site/</p><p><img src=\"https://pic1.afdiancdn.com/user/333022d85d9f11eebef852540025c377/common/025ec22852ae79ab9eae9ccc248530cd_w3338_h1798_s7022.png?imageView2/2/w/1280\" class=\"fr-fic fr-dib\" data-ec=\"200\" data-em=\"上传成功\" data-data=\"[object Object]\"></p><p><img src=\"https://pic1.afdiancdn.com/user/333022d85d9f11eebef852540025c377/common/8b9c138d761f1a09f34e084943c593f6_w1944_h1774_s1800.png?imageView2/2/w/1280\" class=\"fr-fic fr-dib\" data-ec=\"200\" data-em=\"上传成功\" data-data=\"[object Object]\"></p><p><img src=\"https://pic1.afdiancdn.com/user/333022d85d9f11eebef852540025c377/common/ae005739a78276a70947f27d32a76c86_w970_h1768_s1215.png?imageView2/2/w/1280\" class=\"fr-fic fr-dib\" data-ec=\"200\" data-em=\"上传成功\" data-data=\"[object Object]\"></p>",
                    "price": "12.00",
                    "update_time": 1714101669,
                    "timing": {
                        "timing_on": 0,
                        "timing_off": 0
                    },
                    "pay_month": 1,
                    "show_price": "12.00",
                    "show_price_after_adjust": "",
                    "has_coupon": 0,
                    "coupon": [],
                    "favorable_price": -1,
                    "independent": 0,
                    "permanent": 0,
                    "can_buy_hide": 1,
                    "need_address": 0,
                    "product_type": 1,
                    "sale_limit_count": -1,
                    "need_invite_code": false,
                    "bundle_stock": 0,
                    "bundle_sku_select_count": 0,
                    "config": {},
                    "has_plan_config": 0,
                    "shipping_fee_info": {
                        "id": 11,
                        "name": "全国包邮",
                        "user_id": "system",
                        "status": 1,
                        "template_type": 1,
                        "fee_type": 1,
                        "base_fee": "0.00",
                        "base_fee_count": 1,
                        "addup_fee": "0.00",
                        "addup_count": 1,
                        "create_time": 0,
                        "update_time": 0
                    },
                    "expire_time": 2114352000,
                    "sku_processed": [
                        {
                            "sku_id": "fcbe5774fad811eeaaff5254001e7c00",
                            "price": "8.80",
                            "count": 1,
                            "name": "好看的个人主页",
                            "album_id": "",
                            "pic": "",
                            "stock": "",
                            "post_id": ""
                        }
                    ],
                    "rankType": 30
                },
                "all_sum_amount": "8.80",
                "first_pay_time": 1714030286,
                "last_pay_time": 1714030286,
                "user": {
                    "user_id": "e3a0606cb37411e9b8e752540025c377",
                    "name": "Halo",
                    "avatar": "https://pic1.afdiancdn.com/user/e3a0606cb37411e9b8e752540025c377/avatar/dce7df712c32c8b607c44478c3c68144_w1024_h1024_s422.png",
                    "user_private_id": "deca1b527e21bfd92b9081d09fa713b78854277a"
                }
            },
            {
                "sponsor_plans": [],
                "current_plan": {
                    "name": ""
                },
                "all_sum_amount": "10.00",
                "first_pay_time": 1701407849,
                "last_pay_time": 1704104176,
                "user": {
                    "user_id": "e0739c7c900811ee94155254001e7c00",
                    "name": "GIS带师",
                    "avatar": "https://pic1.afdiancdn.com/user/e0739c7c900811ee94155254001e7c00/avatar/63ce5844edaf3da235ccad307f3d9f80_w2560_h1808_s2448.png",
                    "user_private_id": "4009ea5dded9e19212ead26ca4d90d3ef9ca5672"
                }
            }
        ],
        "request": {
            "user_id": "333022d85d9f11eebef852540025c377",
            "params": "{\"page\":1}",
            "ts": 1714360707,
            "sign": "918a2561b165b18cd3c98294a57e4d82"
        }
    }
}
```