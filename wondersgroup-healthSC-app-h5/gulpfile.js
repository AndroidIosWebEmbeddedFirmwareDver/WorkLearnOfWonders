/**
 * Created by stone on 16/5/13.
 */
var gulp         = require('gulp');
var sass         = require('gulp-sass');                //编译sass
var autoprefixer = require('gulp-autoprefixer');        //自动添加厂商前缀
var imagemin     = require('gulp-imagemin');            //压缩图片
var minifyCss    = require('gulp-minify-css');          //压缩css
var uglify       = require('gulp-uglify');              //压缩Js
var rename       = require('gulp-rename');              //重命名
var pngquant     = require('imagemin-pngquant');
var browserSync  = require('browser-sync').create();

var config = {
    scssPath:"client/src/scss/",
    jsPath:"client/src/js/",
    imagesPath:"client/src/images/",
    scssDistPath:"client/static/css/",
    jsDistPath:"client/static/js/",
    imagesDistPath:"client/static/images/",
    viewPath: "client/views/"
};

gulp.task('serve', ['sass','compress','imagemin'], function() {
    browserSync.init({
        proxy: 'http://localhost:3003',
    });
    gulp.watch(config.scssPath+"*/*.scss", ['sass']);
    gulp.watch(config.jsPath+"*/*.js", ['compress']);
    gulp.watch(config.imagesPath+"*/*", ['imagemin']);
    gulp.watch(config.viewPath+ "*/*.ejs").on('change', browserSync.reload);
});

// sass编译+css压缩
gulp.task('sass', function() {
    return gulp.src(config.scssPath+"*/*.scss")
        .pipe(sass())
        .pipe(autoprefixer({
            browsers: ['last 2 version', 'safari 5', 'ie 8', 'ie 9', 'opera 12.1', 'ios 6', 'android 4'],
            cascade: true,
            remove:true
        }))
        .pipe(minifyCss({compatibility: 'ie8'}))
        .pipe(rename({suffix: '.min'}))
        .pipe(gulp.dest(config.scssDistPath))
        .pipe(browserSync.stream(true));
});

//js压缩
gulp.task('compress', function() {
    gulp.src(config.jsPath+'*/*.js')
        .pipe(uglify())
        .pipe(rename({suffix: '.min'}))
        .pipe(gulp.dest(config.jsDistPath))
        .pipe(browserSync.stream());
});

//压缩图片
 gulp.task('imagemin', function(){
     return gulp.src(config.imagesPath+"*/*")
         .pipe(imagemin({
             progressive: true,
             svgoPlugins: [{removeViewBox: false}],//不要移除svg的viewbox属性
             use: [pngquant()] //使用pngquant深度压缩png图片的imagemin插件
         }))
         .pipe(gulp.dest(config.imagesDistPath))
 });

gulp.task('default', ['serve']);
