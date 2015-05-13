<!DOCTYPE HTML>
<html>
<head>
    <title>凯文信息</title>
    <link href="${util.path}/kevininfo/css/bootstrap.css" rel='stylesheet' type='text/css' />
    <script type="text/javascript" src="${util.path}/js/jquery-1.8.3.min.js"></script>
    <!---- start-smoth-scrolling---->
    <script type="text/javascript" src="${util.path}/kevininfo/js/move-top.js"></script>
    <script type="text/javascript" src="${util.path}/kevininfo/js/easing.js"></script>
    <script type="text/javascript">
        jQuery(document).ready(function($) {
            $(".scroll").click(function(event){
                event.preventDefault();
                $('html,body').animate({scrollTop:$(this.hash).offset().top},1000);
            });
        });
    </script>
    <!---- start-smoth-scrolling---->
    <!-- Custom Theme files -->
    <link href="${util.path}/kevininfo/css/theme-style.css" rel='stylesheet' type='text/css' />
    <!-- Custom Theme files -->
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <script type="text/javascript">
        addEventListener("load", function() { setTimeout(hideURLbar, 0); }, false); function hideURLbar(){ window.scrollTo(0,1); }
    </script>
    </script>
    <!----font-Awesome----->
    <link rel="stylesheet" href="${util.path}/kevininfo/fonts/css/font-awesome.min.css" />
        <!----font-Awesome----->
    <!----webfonts--->
    <link href='${util.path}/kevininfo/css/google.css' rel='stylesheet' type='text/css' />
    <!----//webfonts---->
    <!----start-top-nav-script---->
    <script>
    $(function() {
        var pull 		= $('#pull');
        menu 		= $('nav ul');
        menuHeight	= menu.height();
        $(pull).on('click', function(e) {
            e.preventDefault();
            menu.slideToggle();
        });
        $(window).resize(function(){
            var w = $(window).width();
            if(w > 320 && menu.is(':hidden')) {
                menu.removeAttr('style');
            }
        });
    });
    </script>
    <!----//End-top-nav-script---->
</head>
<body>
<!----start-container------>
<!----start-header---->
<div id="home" class="header scroll">
    <div class="container">
        <!---- start-logo---->
        <div class="logo">
            <a href="#"><img src="${util.path}/kevininfo/images/logo.png" title="Blue agency" /></a>
        </div>
        <!---- //End-logo---->
        <!----start-top-nav---->
        <nav class="top-nav">
            <ul class="top-nav">
                <li class="active"><a href="#home" class="scroll">首页</a></li>
                <li class="page-scroll"><a href="#about" class="scroll">公司简介</a></li>
                <li class="page-scroll"><a href="#services" class="scroll">服务</a></li>
                <li class="page-scroll"><a href="#port" class="scroll">成功案例</a></li>
                <li class="page-scroll"><a href="#blog" class="scroll">客户评价</a></li>
                <li class="page-scroll"><a href="#team" class="scroll">研发团队</a></li>
                <li class="page-scroll"><a href="#contact" class="scroll">联系我们</a></li>
            </ul>
            <a href="#" id="pull"><img src="${util.path}/kevininfo/images/nav-icon.png" title="menu" /></a>
        </nav>
        <div class="clearfix"> </div>
        <!----//End-top-nav---->
    </div>
</div>
<!----//End-header---->
<!-----start-slider---->
<div class="slider">
    <script src="${util.path}/kevininfo/js/responsiveslides.min.js"></script>
    <script>
        // You can also use "$(window).load(function() {"
        $(function () {
            // Slideshow 3
            $("#slider3").responsiveSlides({
                manualControls: '#slider3-pager',
            });
        });
    </script>
    <!-- Slideshow -->
    <ul class="rslides" id="slider3">
        <li>
            <img src="${util.path}/kevininfo/images/slider-img2.jpg" alt="">
            <div class="slid-info">
                <h1>大数据企业网站</h1>
                <p>竭诚为您提供企业网站，让您的企业拥有官网，为您的企业提供门户网站，并将您的产品列入大数据领域，有助于产品的推广。</p>
                <a class="slid-btn" href="#">更多</a>
            </div>
        </li>
        <li><img src="${util.path}/kevininfo/images/slider-img3.jpg" alt="">
            <div class="slid-info">
                <h1>微信公众号</h1>
                <p>为您的企业提供微信公众号服务号，并为公众号提供第三方页面，价格低廉，一次购买，终身免费。</p>
                <a class="slid-btn" href="#">更多</a>
            </div>
        </li>
        <li><img src="${util.path}/kevininfo/images/slider-img1.jpg" alt="">
            <div class="slid-info">
                <h1>软件开发及提供解决方案</h1>
                <p>为您提供符合需求的软件，开发人员全部十年以上开发经验，代码健壮性、安全性、高效性无懈可击；并为企业提供软件解决方案。</p>
                <a class="slid-btn" href="#">更多</a>
            </div>
        </li>
    </ul>
    <!-- Slideshow -->
    <ul id="slider3-pager">
        <li><a href="#"><span> </span></a></li>
        <li><a href="#"><span> </span></a></li>
        <li><a href="#"><span> </span></a></li>
    </ul>
</div>
<!-----//End-slider---->
<!----start-about----->
<div id="about" class="about">
    <div class="container">
        <h2>公司简介</h2>
        <p class="wellcome-note-head"><label>深圳市凯文信息有限公司</label>我们致力于：<span>将你的产品展示给有需要的用户</span></p>
        <p class="wellcome-note-info">
            公司基于大数据为基础，为客户提供企业网站、微信公众号和软件解决方案。对于企业来说，网站是必需品，我们能在短时间内为你提供符合世界一流水平的企业网站，如有需要，可提供对应的手机版；为您的企业提供微信公众号，并配套第三方网页，实现灵活定制，让你的产品加入大数据，找到有需要的用户；公司同时提供软件服务解决方案，公司技术人员都是行业精英，能随时开发出符合您需求的软件。
        </p>
        <!----start-about-grids----->
        <div class="about-grids">
            <div class="col-md-3 about-grid">
                <span class="about-icon"> </span>
                <h3><a href="#"><label>企业</label> 网站</a></h3>
                <p>
                    企业网站是企业的门户，是企业在互联网上的名片。每一个企业都必需拥有自己的官网，公司致力于为你建造企业网站、手机版网站，将你的产品展现在互联网，使用户在第一时间能找到您的产品。
                </p>
            </div>
            <div class="col-md-3 about-grid">
                <span class="about-icon1"> </span>
                <h3><a href="#"><label>微信</label> 公众号</a></h3>
                <p>
                    我们为您的企业创建微信公众服务号，并提供第三方定制页面，支持产品的展示，并将产品加入大数据，进行统计分析，使产品能展示在需要的用户面前。
                </p>
            </div>
            <div class="col-md-3 about-grid">
                <span class="about-icon2"> </span>
                <h3><a href="#"><label>软件</label> 开发</a></h3>
                <p>
                    公司为你提供软件定制开发，量身打造，开发符合您特定需要的软件，开发人员都是十年以上开发经验，行业精英，代码优美，性能高，可水平扩展，易于维护。
                </p>
            </div>
            <div class="col-md-3 about-grid">
                <span class="about-icon3"> </span>
                <h3><a href="#"><label>云计算</label> 大数据</a></h3>
                <p>
                    服务器全部采用云服务器，安全可靠，费用低；重要数据加入大数据，采用Hadoop、MapReduce、Hbase、MongoDB、Redis、Memcached等技术进行存储分析计算。
                </p>
            </div>
            <div class="clearfix"> </div>
        </div>
    </div>
    <!----//End-about-grids----->
</div>
<!----//End-about----->
<!----start-services---->
<div id="services" class="services">
    <div class="container">
        <h3>服务<label> </label></h3>
        <!----start-servicves-list---->
        <div class="services-list text-center">
            <ul class="list-unstyled list-inline">
                <li>
                    <a href="#"><span class="service-icon"> </span><label>手机版</label></a>
                </li>
                <li>
                    <a href="#"><span class="service-icon1"> </span><label>网页版</label></a>
                </li>
                <li>
                    <a href="#"><span class="service-icon2"> </span><label>微信公众号</label></a>
                </li>
                <li>
                    <a href="#"><span class="service-icon3"> </span><label>技术支持</label></a>
                </li>
                <li>
                    <a href="#"><span class="service-icon4"> </span><label>大数据分析</label></a>
                </li>
                <li>
                    <a href="#"><span class="service-icon6"> </span><label>软件开发</label></a>
                </li>
            </ul>
        </div>
        <!----//End-servicves-list---->
    </div>
</div>
<!----//End-services---->
<!----start-portfolio----->
<div id="port" class="portfolio portfolio-box">
    <div class="container">
        <h3>成功案例<label> </label></h3>
        <!----start-portfolio---->
        <div id="port" class="container portfolio-main">
            <!---- start-portfolio-script----->
            <script type="text/javascript" src="${util.path}/kevininfo/js/jquery.mixitup.min.js"></script>
            <script type="text/javascript">
                $(function () {
                    var filterList = {
                        init: function () {
                            $('#portfoliolist').mixitup({
                                targetSelector: '.portfolio',
                                filterSelector: '.filter',
                                effects: ['fade'],
                                easing: 'snap',
                                // call the hover effect
                                onMixEnd: filterList.hoverEffect()
                            });

                        },
                        hoverEffect: function () {
                            // Simple parallax effect
                            $('#portfoliolist .portfolio').hover(
                                    function () {
                                        $(this).find('.label').stop().animate({bottom: 0}, 200, 'easeOutQuad');
                                        $(this).find('img').stop().animate({top: -30}, 500, 'easeOutQuad');
                                    },
                                    function () {
                                        $(this).find('.label').stop().animate({bottom: -40}, 200, 'easeInQuad');
                                        $(this).find('img').stop().animate({top: 0}, 300, 'easeOutQuad');
                                    }
                            );

                        }

                    };
                    // Run the show!
                    filterList.init();
                });
            </script>
            <!----//End-portfolio-script----->
            <ul id="filters" class="clearfix">
                <li><span class="filter active" data-filter="app card icon logo web">全部</span> /</li>
                <li><span class="filter" data-filter="app">绍兴五金</span> /</li>
                <li><span class="filter" data-filter="card">粤友塑料</span> /</li>
                <li><span class="filter" data-filter="icon">深圳超意</span> /</li>
                <li><span class="filter" data-filter="web">松奎包装</span></li>
            </ul>
            <div id="portfoliolist">
                <div class="portfolio app mix_all" data-cat="app" style="display: inline-block; opacity: 1;">
                    <div class="portfolio-wrapper">
                        <a data-toggle="modal" data-target=".bs-example-modal-md" href="#" class="b-link-stripe b-animate-go  thickbox">
                            <img class="p-img" src="${util.path}/kevininfo/images/p2.jpg" /></a>
                    </div>
                    <div class="port-info">
                        <h4><a href="#">绍兴五金厂</a></h4>
                        <span>网页</span>
                    </div>
                </div>
                <div class="portfolio web mix_all" data-cat="web" style="display: inline-block; opacity: 1;">
                    <div class="portfolio-wrapper">
                        <a data-toggle="modal" data-target=".bs-example-modal-md" href="#" class="b-link-stripe b-animate-go  thickbox">
                            <img class="p-img" src="${util.path}/kevininfo/images/p3.jpg" /></a>
                    </div>
                    <div class="port-info">
                        <h4><a href="#">松奎包装</a></h4>
                        <span>网页</span>
                    </div>
                </div>
                <div class="portfolio icon mix_all" data-cat="icon" style="display: inline-block; opacity: 1;">
                    <div class="portfolio-wrapper">
                        <a data-toggle="modal" data-target=".bs-example-modal-md" href="#" class="b-link-stripe b-animate-go  thickbox">
                            <img class="p-img" src="${util.path}/kevininfo/images/p4.jpg" /></a>
                    </div>
                    <div class="port-info">
                        <h4><a href="#">深圳超意</a></h4>
                        <span>网页</span>
                    </div>
                </div>
                <div class="portfolio app mix_all" data-cat="app" style="display: inline-block; opacity: 1;">
                    <div class="portfolio-wrapper">
                        <a data-toggle="modal" data-target=".bs-example-modal-md" href="#" class="b-link-stripe b-animate-go  thickbox">
                            <img class="p-img" src="${util.path}/kevininfo/images/p5.jpg" /></a>
                    </div>
                    <div class="port-info">
                        <h4><a href="#">绍兴五金厂</a></h4>
                        <span>网页</span>
                    </div>
                </div>
                <div class="portfolio card mix_all" data-cat="card" style="display: inline-block; opacity: 1;">
                    <div class="portfolio-wrapper">
                        <a data-toggle="modal" data-target=".bs-example-modal-md" href="#" class="b-link-stripe b-animate-go  thickbox">
                            <img class="p-img" src="${util.path}/kevininfo/images/p6.jpg" /></a>
                    </div>
                    <div class="port-info">
                        <h4><a href="#">粤友塑料</a></h4>
                        <span>网页</span>
                    </div>
                </div>
                <div class="portfolio icon mix_all" data-cat="icon" style="display: inline-block; opacity: 1;">
                    <div class="portfolio-wrapper">
                        <a data-toggle="modal" data-target=".bs-example-modal-md" href="#" class="b-link-stripe b-animate-go  thickbox">
                            <img class="p-img" src="${util.path}/kevininfo/images/p7.jpg" /></a>
                    </div>
                    <div class="port-info">
                        <h4><a href="#">深圳超意</a></h4>
                        <span>手机网页版</span>
                    </div>
                </div>
                <div class="portfolio app mix_all" data-cat="app" style="display: inline-block; opacity: 1;">
                    <div class="portfolio-wrapper">
                        <a data-toggle="modal" data-target=".bs-example-modal-md" href="#" class="b-link-stripe b-animate-go  thickbox">
                            <img class="p-img" src="${util.path}/kevininfo/images/p8.jpg" /></a>
                    </div>
                    <div class="port-info">
                        <h4><a href="#">绍兴五金厂</a></h4>
                        <span>手机网页版</span>
                    </div>
                </div>
                <div class="portfolio card mix_all" data-cat="card" style="display: inline-block; opacity: 1;">
                    <div class="portfolio-wrapper">
                        <a data-toggle="modal" data-target=".bs-example-modal-md" href="#" class="b-link-stripe b-animate-go  thickbox">
                            <img class="p-img" src="${util.path}/kevininfo/images/p9.jpg" /></a>
                    </div>
                    <div class="port-info">
                        <h4><a href="#">粤友塑料</a></h4>
                        <span>手机网页版</span>
                    </div>
                </div>
                <div class="clearfix"> </div>
                <a class="more-ports text-center" href="#"><span> </span></a>
            </div>
        </div>
    </div>
    <div class="clearfix"> </div>
</div>
<!----//End-portfolio---->
<!-----start-testmonials---->
<div class="testmonials">
    <div class="container">
        <div class="client-pic col-md-4">
            <img src="${util.path}/kevininfo/images/client.jpg" title="name" />
        </div>
        <div class="client-sys col-md-8">
            <!----start-testmonial-slider---->
            <link rel="stylesheet" href="${util.path}/kevininfo/css/testmonial.css">
            <script src="${util.path}/kevininfo/js/prefixfree.min.js"></script>
            <!----//End-testmonial-slider---->
            <div class="gallery autoplay items-3">
                <div id="item-1" class="control-operator"></div>
                <div id="item-2" class="control-operator"></div>
                <div id="item-3" class="control-operator"></div>
                <figure class="item">
                    <h3>客户评价:</h3>
                    <p class="client-says"><span class="left-quit"> &#8220;</span>我们公司自从做了网站后，给别人介绍方便多了，名片上也多了一行：公司网站，以前还愁公司没网址，现在显得正规很多，产品也可以在上面展示，有些客户甚至通过网站直接找到我们。<span class="right-quit"> &#8221;</span></p>
                </figure>
                <figure class="item">
                    <h3>客户评价 :</h3>
                    <p class="client-says"><span class="left-quit"> &#8220;</span>微信公众服务号申请烦锁，之前本来想自己弄，结果弄了一段时间，放弃了。现在好了，自从有了微信公众号，别人添加后，直接就可以发信息过来洽谈页面。还赠送了定制的页面，真的是与众不同。<span class="right-quit"> &#8221;</span></p>
                </figure>
                <figure class="item">
                    <h3>客户评价:</h3>
                    <p class="client-says"><span class="left-quit"> &#8220;</span>之前一直想找软件公司做软件，但都被他们的天价吓倒，没想到通过这里，就这么简单地实现了我想要的软件，真惊叹这些程序员的能力。<span class="right-quit"> &#8221;</span></p>
                </figure>
                <div class="clearfix"> </div>
                <div class="controls">
                    <a href="#item-1" class="control-button"><span> </span></a>
                    <a href="#item-2" class="control-button"><span> </span></a>
                    <a href="#item-3" class="control-button"><span> </span></a>
                </div>
                <div class="clearfix"> </div>
            </div>
        </div>
        <div class="clearfix"> </div>
    </div>
</div>
<!-----//End-testmonials---->
<!----//End-portfolio----->
<!-----start-team----->
<div id="team" class="team">
    <div class="container">
        <div class="team-members text-center">
            <div class="col-md-4 team-member">
                <a class="member-pic" href="#"><img src="${util.path}/kevininfo/images/t1.jpg" title="member-name" /></a>
                <h3><a href="#">Kevin Zhang</a></h3>
                <span>架构师</span>
                <p>十年软件开发经验，从事过电信、电子政务、电子商务、移动互联网、互联网+ 等方面的工作，独立开发过几个系统，专注于大数据领域。</p>
                <ul class="list-unstyled list-inline social-icons">
                    <li><a href="#"><i class="fa fa-twitter"> </i></a></li>
                    <li><a href="#"><i class="fa fa-facebook"> </i></a></li>
                    <li><a href="#"><i class="fa fa-dribbble"> </i> </a></li>
                </ul>
            </div>
            <div class="col-md-4 team-member">
                <a class="member-pic" href="#"><img src="${util.path}/kevininfo/images/t2.jpg" title="member-name" /></a>
                <h3><a href="#">Bruce Liu</a></h3>
                <span>软件工程师</span>
                <p>多年专业从事软件开发工作，专注于企业网站、微信开放平台，大型企业应用，在安全领域、高并发、高性能方面有突出成绩。</p>
                <ul class="list-unstyled list-inline social-icons">
                    <li><a href="#"><i class="fa fa-twitter"> </i></a></li>
                    <li><a href="#"><i class="fa fa-facebook"> </i></a></li>
                    <li><a href="#"><i class="fa fa-dribbble"> </i> </a></li>
                </ul>
            </div>
            <div class="col-md-4 team-member">
                <a class="member-pic" href="#"><img src="${util.path}/kevininfo/images/t3.jpg" title="member-name" /></a>
                <h3><a href="#">Young Lv</a></h3>
                <span>前端工程师</span>
                <p>专业从事前端工作，多年从事html、css、javascript工作，有丰富的用户体验方面的相关经验，在多家公司设计过高端的用户界面，界面达到世界一流水平。</p>
                <ul class="list-unstyled list-inline social-icons">
                    <li><a href="#"><i class="fa fa-twitter"> </i></a></li>
                    <li><a href="#"><i class="fa fa-facebook"> </i></a></li>
                    <li><a href="#"><i class="fa fa-dribbble"> </i> </a></li>
                </ul>
            </div>
        </div>
    </div>
</div>
<!-----//End-team----->
<!-----start-brand-carsuals------>
<script type="text/javascript" src="${util.path}/kevininfo/js/jquery.flexisel.js"></script>
<div class="brand-carsuals">
    <ul id="flexiselDemo3">
        <li><img src="${util.path}/kevininfo/images/b-logo.png" /></li>
        <li><img src="${util.path}/kevininfo/images/b-logo.png" /></li>
        <li><img src="${util.path}/kevininfo/images/b-logo.png" /></li>
        <li><img src="${util.path}/kevininfo/images/b-logo.png" /></li>
    </ul>
    <script type="text/javascript">
        $(window).load(function() {
            $("#flexiselDemo3").flexisel({
                visibleItems: 5,
                animationSpeed: 1000,
                autoPlay: true,
                autoPlaySpeed: 3000,
                pauseOnHover: true,
                enableResponsiveBreakpoints: true,
                responsiveBreakpoints: {
                    portrait: {
                        changePoint:480,
                        visibleItems: 1
                    },
                    landscape: {
                        changePoint:640,
                        visibleItems: 2
                    },
                    tablet: {
                        changePoint:768,
                        visibleItems: 3
                    }
                }
            });

        });
    </script>
</div>
<!-----//End-brand-carsuals------>
<!----start-blog---->
<div id="blog" class="blog">
    <div class="container">
        <h3>客户评价<label> </label></h3>
        <div class="blog-grids">
            <div class="col-md-4 blog-grid-row frist-row">
                <a class="post-pic" href="#"><img src="${util.path}/kevininfo/images/post-pic.jpg" title="pic-name" /></a>
                <!----start-artical-info---->
                <div class="artical-info">
                    <div class="post-head">
                        <div class="post-head-left">
                            <h4><a href="#">网站的建设符合当下互联网风格</a></h4>
                            <p class="author">By <a href="#">甄先生</a></p>
                        </div>
                        <div class="post-head-right">
                            <span>17</span><label>四月 2015</label>
                        </div>
                        <div class="clearfix"> </div>
                    </div>
                    <p class="post-text">我们多年从事手提包的生产与销售，一直是线下销售，近年来电商的发展，我们在淘宝、天猫上都有开店，但没有公司官网，一直没有一个在互联网全面介绍公司的地方，现在终于找到了。</p>
                    <div class="post-bottom">
                        <div class="post-bottom-left">
                            <p><a href="#">10 评价</a> | <i class="fa fa-heart"> </i> 151</p>
                        </div>
                        <div class="post-bottom-right">
                            <a class="artbtn" href="#">更多</a>
                        </div>
                        <div class="clearfix"> </div>
                    </div>
                </div>
                <!----//End-artical-info---->
            </div>
            <!---->
            <div class="col-md-4 artical-row2">
                <!----start-artical-info---->
                <div class="artical-info">
                    <div class="post-head post-head1">
                        <div class="post-head-left">
                            <h4><a href="#">微信公众号是我给客户的一张名片</a></h4>
                            <p class="author">By <a href="#">王先生</a></p>
                        </div>
                        <div class="post-head-right post-head-right-C">
                            <span>10</span><label>四月 2015</label>
                        </div>
                        <div class="clearfix"> </div>
                    </div>
                    <p class="post-text">我一直是做服装辅料的，由于文化水平低，不懂互联网，之前经朋友介绍，找到了这家公司，帮我做了一个微信公众号，顺便做下广告，我的公众号为“chaoyi”，没想到做完后，他们还送了我一个第三方的网站，我现在所有产品都是通过第三方网站挂到公众号上。</p>
                    <div class="post-bottom">
                        <div class="post-bottom-left">
                            <p><a href="#">10 评价</a> | <i class="fa fa-heart"> </i> 151</p>
                        </div>
                        <div class="post-bottom-right">
                            <a class="artbtn artbtn1" href="#">更多</a>
                        </div>
                        <div class="clearfix"> </div>
                    </div>
                </div>
                <!----//End-artical-info---->
                <!----start-artical-info---->
                <div class="artical-info">
                    <div class="post-head post-head1">
                        <div class="post-head-left">
                            <h4><a href="#">我的五金厂也有自己的网站啦</a></h4>
                            <p class="author">By <a href="#">谢先生</a></p>
                        </div>
                        <div class="post-head-right post-head-right-C">
                            <span>23</span><label>四月 2013</label>
                        </div>
                        <div class="clearfix"> </div>
                    </div>
                    <p class="post-text">Kevin是我的高中同学，当时是在一次聊天中，我得知我们厂也可以做个网站介绍自己的产品，于是就找他开发，现在这网站一直在使用，可以自己更换产品，修改图片、修改说明。很多客户直接上网站看，省去了很多接电话的时间。</p>
                    <div class="post-bottom">
                        <div class="post-bottom-left">
                            <p><a href="#">10 评价</a> | <i class="fa fa-heart"> </i> 151</p>
                        </div>
                        <div class="post-bottom-right">
                            <a class="artbtn artbtn1" href="#">更多</a>
                        </div>
                        <div class="clearfix"> </div>
                    </div>
                </div>
                <!----//End-artical-info---->
            </div>
            <!---->
            <div class="col-md-4 artical-row2">
                <!----start-artical-info---->
                <div class="artical-info">
                    <div class="post-head post-head1">
                        <div class="post-head-left">
                            <h4><a href="#">确定是一个技术过硬的团队</a></h4>
                            <p class="author">By <a href="#">张先生</a></p>
                        </div>
                        <div class="post-head-right post-head-right-C">
                            <span>28</span><label>五月 2015</label>
                        </div>
                        <div class="clearfix"> </div>
                    </div>
                    <p class="post-text">我们公司是做数据分析的，之前需抓取其他网站的一些数据，由于有的是登录后才能看到，鉴于研发能力，暂时无法实现，后来找了一家公司，别人开价8万，之后又找到这家，只要3万就成交。现在数据实时更新，从没出错。</p>
                    <div class="post-bottom">
                        <div class="post-bottom-left">
                            <p><a href="#">10 评价</a> | <i class="fa fa-heart"> </i> 151</p>
                        </div>
                        <div class="post-bottom-right">
                            <a class="artbtn artbtn1" href="#">更多</a>
                        </div>
                        <div class="clearfix"> </div>
                    </div>
                </div>
                <!----//End-artical-info---->
                <!----start-artical-info---->
                <div class="artical-info">
                    <div class="post-head post-head1">
                        <div class="post-head-left">
                            <h4><a href="#">客户要包装材料，可以用手机看啦</a></h4>
                            <p class="author">By <a href="#">张先生</a></p>
                        </div>
                        <div class="post-head-right post-head-right-C">
                            <span>30</span><label>四月 2013</label>
                        </div>
                        <div class="clearfix"> </div>
                    </div>
                    <p class="post-text">我开的是一个小工厂，由于在农村，科技落后，找不到开发人员帮我做网站，后来找了这家公司，不仅做了电脑端的网站，还做了手机端的页面，现在通过手机也可以看到我的包装材料，给客户介绍时，方便很多。</p>
                    <div class="post-bottom">
                        <div class="post-bottom-left">
                            <p><a href="#">10 评价</a> | <i class="fa fa-heart"> </i> 151</p>
                        </div>
                        <div class="post-bottom-right">
                            <a class="artbtn artbtn1" href="#">更多</a>
                        </div>
                        <div class="clearfix"> </div>
                    </div>
                </div>
                <!----//End-artical-info---->
            </div>
        </div>
    </div>
</div>
<!----//End-blog---->
<!----start-contact---->
<div id="contact" class="contact">
    <div class="container">
        <h3>联系我们<label> </label></h3>
        <div class="contact-grids">

            <!----->
            <div class="col-md-6 contact-grid-right">
                <p>如需要开发网站、微信公众号、微信公众号第三方页面支持、开发软件或进行大数据分析，可通过以下方式与我们联系。</p>
                <div class="col-md-4">
                    <h5>电话</h5>
                    <span>18902467883</span>
                </div>
                <div class="col-md-4">
                    <h5>地址</h5>
                    <span>深圳市南山区</span>
                    <span>科技园软件产业基地A栋</span>
                </div>
                <div class="col-md-4">
                    <h5>Email</h5>
                    <span><a href="#">87863823@qq.com</a></span>
                </div>
                <div class="clearfix"> </div>
            </div>
            <div class="clearfix"> </div>
        </div>
        <!-----start-contact-social---->
        <ul class="list-unstyled list-inline contact-social">
            <li><a href="#"><i class="fa fa-facebook"></i></a></li>
            <li><a href="#"><i class="fa fa-google-plus"></i></a></li>
            <li><a href="#"><i class="fa fa-linkedin"></i></a></li>
            <li><a href="#"><i class="fa fa-twitter"></i></a></li>
            <li><a href="#"><i class="fa fa-skype"></i></a></li>
        </ul>
        <!-----//End-contact-social---->
    </div>
</div>
<!----//End-contact---->
<!----start-copy-right--->
<div class="copy-right">
    <div class="container">
        <p>© 凯文信息 ${year }粤ICP备14009815号</p>
        <script type="text/javascript">
            $(document).ready(function() {
                $().UItoTop({ easingType: 'easeOutQuart' });
            });
        </script>
        <a href="#" id="toTop" style="display: block;"> <span id="toTopHover" style="opacity: 1;"> </span></a>
    </div>
</div>
<!----//End-copy-right--->
<!----//End-container------>
</body>
</html>
