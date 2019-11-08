# poster-util
> ps：自定义字体、模板图片、网络图片缓存路径均可配置。具体配置参考 [poster.properties]
##在resource目录下新建fonts templates image download这四个文件夹
#字体的路径
poster-fonts-path=C:\\Users\\appadmin\\Desktop\\demo\\learn\\poster\\src\\main\\resources\\fonts
#背景模板的路径
poster-templates-path=C:\\Users\\appadmin\\Desktop\\demo\\learn\\poster\\src\\main\\resources\\templates
#图片生成路径
poster-image-path=C:\\Users\\appadmin\\Desktop\\demo\\learn\\poster\\src\\main\\resources\\image
#图片下载路径
poster-download-path=C:\\Users\\appadmin\\Desktop\\demo\\learn\\poster\\src\\main\\resources\\download
#json文件路径
poster-jsonFile-path=C:\\Users\\appadmin\\Desktop\\poster-util\\src\\main\\resource\\templates.json
## png转jpg图片占存大小变小，jgp转png图片占存大小变大
### config字段

| 字段            | 类型                     | 必填 | 描述                                       |
| --------------- | ------------------------ | ---- | ------------------------------------------ |
| width           | Number(单位:px)          | 是   | 画布宽度                                   |
| height          | Number(单位:px)          | 是   | 画布高度                                   |
| backgroundColor | String                   | 否   | 画布颜色                                   |
| desFileSize     | long                     | 否   | 指定图片大小,单位kb                        |
| accuracy        | double                   | 否   | 精度,递归压缩的比率,建议小于0.9,等于1会发生死循环|
| blocks          | Object Array（对象数组） | 否   | 看下文                                     |
| texts           | Object Array（对象数组） | 否   | 看下文                                     |
| images          | Object Array（对象数组） | 否   | 看下文                                     |
| lines           | Object Array（对象数组） | 否   | 看下文                                     |

### blocks字段

| 字段名          | 类型             | 必填 | 描述                                   |
| --------------- | ---------------- | ---- | -------------------------------------- |
| x               | Number(单位:px) | 是   | 块的坐标                               |
| y               | Number(单位:px) | 是   | 块的坐标                               |
| width           | Number(单位:px) | 否   | 如果内部有文字，由文字宽度和内边距决定 |
| height          | Number(单位:px) | 是   |                                        |
| paddingLeft     | Number(单位:px) | 否   | 内左边距                               |
| paddingRight    | Number(单位:px) | 否   | 内右边距                               |
| borderWidth     | Number(单位:px) | 否   | 边框宽度                               |
| borderColor     | String           | 否   | 边框颜色                               |
| backgroundColor | String           | 否   | 背景颜色                               |
| borderRadius    | Number(单位:px) | 否   | 圆角                                   |
| text            | Object           | 否   | 块里面可以填充文字，参考texts字段解释  |
| index          | Int              | 否   | 层级，越大越高                         |

### texts字段

| 字段名         | 类型             | 必填 | 描述                                                         |
| -------------- | ---------------- | ---- | ------------------------------------------------------------ |
| x              | Number(单位:px) | 是   | 坐标                                                         |
| y              | Number(单位:px) | 是   | 坐标                                                         |
| text           | String\|Object   | 是   | 当Object类型时，参数为text字段的参数，marginLeft、marginRight这两个字段可用（示例请看下文） |
| fontSize       | Number(单位:px) | 是   | 文字大小                                                     |
| color          | String           | 否   | 颜色                                                         |
| lineHeight     | Number(单位:px) | 否   | 行高                                                         |
| lineNum        | Int              | 否   | 根据宽度换行，最多的行数                                     |
| width          | Number(单位:px) | 否   | 没有指定为画布宽度，默认为x轴右边所有宽度                                           |
| baseLine       | String           | 否   | top\| middle\|bottom基线对齐方式                             |
| textAlign      | String           | 否   | left\|center\|right对齐方式                                  |
| index         | Int              | 否   | 层级，越大越高                                               |
| font          | String           | 否   | 默认字体为'pingfangtf' ，支持自定义字体      |

### images字段

| 字段         | 类型             | 必填 | 描述                                      |
| ------------ | ---------------- | ---- | ----------------------------------------- |
| x            | Number(单位:px) | 是   | 右上角的坐标                              |
| y            | Number(单位:px) | 是   | 右上角的坐标                              |
| url          | String           | 是   | 图片url（**需要添加到下载白名单域名中**）也支持本地图片 |
| width        | Number(单位:px) | 是   | 宽度（**会根据图片的尺寸同比例缩放**）    |
| height       | Number(单位:px) | 是   | 高度（**会根据图片的尺寸同比例缩放**）    |
| borderRadius | Number(单位:px) | 否   | 圆角，跟css一样                           |
| index       | Int              | 否   | 层级，越大越高                            |
| qrCode       | Bool            | 否   | 是否二维码图片，如果是，url内容就是二维码内容  |
| qrCodeMargin | int             | 否   | 二维码边距                                |
##已经找到问题所在了，因为之前 把 url="www.baidu.com"传的，但实际上 应该把 url="https://www.baidu.com/"
##才能实现真正的自动跳转页面。主要就是要输入完整的网址



### lines字段

| 字段   | 类型             | 必填 | 描述           |
| ------ | ---------------- | ---- | -------------- |
| startX | Number(单位:px) | 是   | 起始坐标       |
| startY | Number(单位:px) | 是   | 起始坐标       |
| endX   | Number(单位:px) | 是   | 终结坐标       |
| endY   | Number(单位:px) | 是   | 终结坐标       |
| width  | Number(单位:px) | 是   | 线的宽度       |
| color  | String           | 否   | 线的颜色       |
| index | Int              | 否   | 层级，越大越高 |

## 接口
    @PostMapping(value = "/poster")
        public ResponseTemplate<File> draw(@RequestBody @Valid Poster poster) throws BusinessException, IOException {
            File file = PosterUtil.draw(poster);
            if (file !=null){
                return new ResponseTemplate<>(200, "图片生成成功", file);
            }else {
                return new ResponseTemplate<>(400, "图片生成失败", null);
            }
        }
##main函数调用
public class App {
    public static void main(String[] args) throws IOException {
        Poster poster = FileUtil.JsonToPoster();
        File file = PosterUtil.draw(poster);
        System.out.println(file);
    }
}       
##依赖
 <dependency>
            <groupId>com.alibaba</groupId>
            <artifactId>fastjson</artifactId>
            <version>1.2.59</version>
        </dependency>
  <dependency>
            <groupId>net.coobird</groupId>
            <artifactId>thumbnailator</artifactId>
            <version>0.4.8</version>
        </dependency>
 <dependency>
      <groupId>com.google.zxing</groupId>
      <artifactId>core</artifactId>
      <version>3.3.3</version>
    </dependency>
    <dependency>
      <groupId>javax.validation</groupId>
      <artifactId>validation-api</artifactId>
      <version>2.0.1.Final</version>
    </dependency>
    <dependency>
      <groupId>org.projectlombok</groupId>
      <artifactId>lombok</artifactId>
      <version>1.18.8</version>
    </dependency>
    <dependency>
      <groupId>com.google.zxing</groupId>
      <artifactId>javase</artifactId>
      <version>3.3.3</version>
    </dependency>
    
##模板用例
用json传递
{
    "width": 640,
    "height": 1034,
    "backgroundColor": "#d04c44",
    "desFileSize":300,
    "accuracy":0.9,
    "blocks": [
        {
            "x": 25,
            "y": 25,
            "width": 590,
            "height": 820,
            "borderColor": "#ffe6c0",
            "borderWidth": 2
        },
        {
            "x": 0,
            "y": 870,
            "width": 640,
            "height": 164,
            "backgroundColor": "#fff"
        },
        {
            "x": 67,
            "y": 303,
            "width": 506,
            "height": 500,
            "backgroundColor": "#fff"
        }
    ],
    "texts": [
        {
            "text": "Lam",
            "x": 320,
            "y": 187,
            "fontSize": 18,
            "lineHeight": 18,
            "color": "#ffe6c0",
            "width": 320,
            "lineNum": 1,
            "baseLine": "middle",
            "textAlign": "center"
        },
        {
            "text": "{个性签名}",
            "x": 320,
            "y": 225,
            "fontSize": 20,
            "lineHeight": 24,
            "color": "#ffe6c0",
            "width": 480,
            "lineNum": 2,
            "baseLine": "middle",
            "textAlign": "center"
        },
        {
            "text": "{小程序码描述}",
            "x": 170,
            "y": 923,
            "fontSize": 18,
            "color": "#999",
            "width": 300,
            "lineNum": 2,
            "baseLine": "middle",
            "zIndex": 8,
            "lineHeight": 40
        }
    ],
    "images": [
        {
            "url": "avatar.png",
            "x": 270,
            "y": 67,
            "width": 100,
            "height": 100,
            "borderRadius": 100
        },
        {
            "url": "main.png",
            "x": 87,
            "y": 323,
            "width": 466,
            "height": 460
        },
        {
            "url": "code.png",
            "x": 45,
            "y": 905,
            "width": 100,
            "height": 100,
            "zIndex": 9
        }
    ],
    "lines":[]