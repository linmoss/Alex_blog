package org.Alex.controller;

import org.Alex.common.ArticleSearch;
import org.Alex.common.LRUCache;
import org.Alex.common.PageHelper;
import org.Alex.common.Result;
import org.Alex.common.WebSite;
import org.Alex.enums.ErrorCodeEnum;
import org.Alex.enums.ResultEnum;
import org.Alex.exception.LWNotFindException;
import org.Alex.pojo.Article;
import org.Alex.pojo.Category;
import org.Alex.pojo.Comment;
import org.Alex.pojo.Tag;
import org.Alex.service.ArticleService;
import org.Alex.service.BannerService;
import org.Alex.service.CategoryService;
import org.Alex.service.CommentService;
import org.Alex.service.FriendlyService;
import org.Alex.service.NavigationService;
import org.Alex.service.TagService;
import org.Alex.utils.CookieUtil;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.thymeleaf.util.StringUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;


@Controller
public class IndexController {

    private final FriendlyService friendlyService;
    private final NavigationService navigationService;
    private final TagService tagService;
    private final ArticleService articleService;
    private final BannerService bannerService;
    private final CategoryService categoryService;
    private final CommentService commentService;

    private final LRUCache lruCache;

    private final WebSite webSite;

    public IndexController(FriendlyService friendlyService, NavigationService navigationService, TagService tagService, ArticleService articleService, BannerService bannerService, CategoryService categoryService, CommentService commentService, LRUCache lruCache, WebSite webSite) {
        this.friendlyService = friendlyService;
        this.navigationService = navigationService;
        this.tagService = tagService;
        this.articleService = articleService;
        this.bannerService = bannerService;
        this.categoryService = categoryService;
        this.commentService = commentService;
        this.lruCache = lruCache;
        this.webSite = webSite;
    }

    @ModelAttribute
    private void indexModel(Model model) {
        // todo 可以在这里定义这个controller公用的model的属性
        model.addAttribute("friendlies", friendlyService.list());
        model.addAttribute("tags", tagService.list(30));
        model.addAttribute("navigations", navigationService.show());
        model.addAttribute("hots", articleService.hotList(5));
        int size = lruCache.size();
        List<String> keywords = lruCache.list();
        Collections.reverse(keywords);
        if (size < 10) {
            keywords.addAll(tagService.list(10 - size).stream().map(Tag::getName).collect(Collectors.toList()));
        }
        model.addAttribute("keywords", keywords);
    }

    @GetMapping("/")
    public String index(@RequestParam(required = false, defaultValue = "1") Integer pn, Model model) {
        model.addAttribute("articlePage", articleService.search(ArticleSearch.indexShow(pn, 6)));
        model.addAttribute("banners", bannerService.list());
        return "index";
    }

    @GetMapping("/category/{id}.html")
    public String categoryList(@PathVariable Integer id, @RequestParam(required = false, defaultValue = "1") Integer pn, Model model) throws LWNotFindException {
        Category category = categoryService.detail(id);
        if (Objects.isNull(category)){
            throw new LWNotFindException();
        }
        model.addAttribute("category", category);
        ArticleSearch articleSearch = ArticleSearch.indexShow(pn, 8);
        articleSearch.setCid(id);
        PageHelper<Article> articlePage = articleService.search(articleSearch);
        model.addAttribute("articlePage", articlePage);
        model.addAttribute("pageType", "category");
        return "list";
    }

    @GetMapping("/tag/{id}.html")
    public String tagList(@PathVariable Integer id, @RequestParam(required = false, defaultValue = "1") Integer pn, Model model) throws LWNotFindException {
        Tag tag = tagService.detail(id);
        if (Objects.isNull(tag)){
            throw new LWNotFindException();
        }
        model.addAttribute("tag", tag);
        ArticleSearch articleSearch = ArticleSearch.indexShow(pn, 8);
        articleSearch.setTid(id);
        PageHelper<Article> articlePage = articleService.search(articleSearch);
        model.addAttribute("articlePage", articlePage);
        model.addAttribute("pageType", "tag");
        return "list";
    }

    @GetMapping("/search.html")
    public String search(@RequestParam(required = false, defaultValue = "1") Integer pn, String keyword, Model model) {
        lruCache.add(keyword);
        model.addAttribute("keyword", keyword);
        ArticleSearch articleSearch = ArticleSearch.indexShow(pn, 8);
        articleSearch.setKeyword(keyword);
        PageHelper<Article> articlePage = articleService.search(articleSearch);
        model.addAttribute("articlePage", articlePage);
        model.addAttribute("pageType", "search");
        return "list";
    }

    @GetMapping("/category.html")
    public String category(Model model) {
        model.addAttribute("categories", categoryService.show());
        return "category";
    }

    @GetMapping("/tag.html")
    public String tag(Model model) {
        model.addAttribute("tagList", tagService.show());
        return "tags";
    }

    @GetMapping("/error/404.html")
    public String error404(Model model) {
        model.addAttribute("error", ErrorCodeEnum.ERROR_404);
        return "fail";
    }

    @GetMapping("/error/500.html")
    public String error500(Model model) {
        model.addAttribute("error", ErrorCodeEnum.ERROR_500);
        return "fail";
    }

    @GetMapping("/{id}.html")
    public String detail(@PathVariable Integer id, Model model, HttpServletRequest request, HttpServletResponse response) throws LWNotFindException {
        Article article = articleService.detail(id);
        if (Objects.isNull(article)) {
            throw new LWNotFindException();
        }
        if (Objects.isNull(CookieUtil.getCookie(request, Article.VIEW_PREFIX + id))) {
            articleService.viewArticle(id);
            CookieUtil.setCookie(response, Article.VIEW_PREFIX + id, "true");
        }
        model.addAttribute("article", article);
        PageHelper<Comment> commentPage = commentService.list(id, 1);
        model.addAttribute("commentPage", commentPage);
        return "detail";
    }

    @PostMapping("/comment")
    @ResponseBody
    public Result<String> comment(Comment comment) {
        if (StringUtils.equals(comment.getEmail(), webSite.getMail())) {
            return Result.error(ResultEnum.RESULT_MAIL_FAIL);
        }
        commentService.save(comment);
        return Result.success();
    }

    @GetMapping("/comment/{id}")
    public String comments(@PathVariable Integer id, @RequestParam(required = false, defaultValue = "1") Integer pn, Model model) {
        pn = Math.max(1, pn);
        PageHelper<Comment> commentPage = commentService.list(id, pn);
        model.addAttribute("commentPage", commentPage);
        return "detail::comments";
    }
}
