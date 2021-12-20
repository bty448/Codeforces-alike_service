<#-- @ftlvariable name="singlePost" type="ru.itmo.wp.domain.Post" -->
<#import "commons.ftlh" as c>

<@c.page>
    <@c.post post=singlePost titleIsLink=false/>
    <#if user??>
        <div class="comment-form">
            <div class="header">Write Comment</div>
            <div class="body">
                <form method="post" action="">
                    <input type="hidden" name="action" value="writeComment"/>
                    <div class="field">
                        <div class="value">
                            <textarea id="text" name="text">${comment.text!}</textarea>
                        </div>
                        <@c.error "comment.text"/>
                    </div>
                    <div class="button-field">
                        <input type="submit" value="Write">
                    </div>
                </form>
            </div>
        </div>
    </#if>
    <#list singlePost.comments as comment>
        <div class="comment">
            <div class="author">${comment.user.login}</div>
            <div class="text">${comment.text}</div>
        </div>
    </#list>
</@c.page>
