package cn.congee.api.util;

import cn.congee.api.common.PageResult;
import cn.congee.api.exception.ServiceException;
import com.alibaba.fastjson.JSON;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.util.CollectionUtils;

import java.util.List;

/**
 * 分页插件扩展
 *
 * @Author: yang
 * @Date: 2020-12-03 15:24
 */
public final class PageExtHelper extends PageHelper {

    /**
     * 只查询数据总量
     *
     * @param select:查询
     * @param <T>
     * @return
     */
    public static <T> PageResult<T> count(Select select){
        PageHelper.startPage(1, -1, true);
        return PageResult.convert(new PageInfo(select.select()));
    }

    /**
     * 只查询分页数据
     *
     * @param page
     * @param size
     * @param select
     * @param <E>
     * @return
     */
    public static <E> PageResult<E> pageingNoCount(int page, int size, Select select){
        return pageingNoCount(page, size, select, null, null);
    }

    public static <E> PageResult<E> pageingNoCount(int page, int size, Select select, Class<E> cls, Convertor<E> convertor){
        validPageSize(page, size);
        PageHelper.startPage(page + 1, size, false, null, false);
        PageResult<E> result = PageResult.convert(new PageInfo(select.select()));
        if(CollectionUtils.isEmpty(result.getModels())){
            //最后一页
            result.setLast(true);
            result.setNumber(0);
            if(page > 0){
                result.setFirst(false);
            }else {
                result.setFirst(true);
            }
        }else if(result.getModels().size() == size){
            result.setLast(false);
            result.setNumber(page);
            if(page > 0){
                result.setFirst(false);
            }else {
                result.setFirst(true);
            }
        }else { //result.getModels().size() < size
            result.setLast(true);
            result.setNumber(page);
            if(page > 0){
                result.setFirst(false);
            }else {
                result.setFirst(true);
            }
        }
        if(cls != null){
            List<E> models = JSON.parseArray(JSON.toJSONString(result.getModels()), cls);
            result.setModels(models);
        }
        if(convertor != null){
            convertor.convert(result.getModels());
        }
        return result;
    }

    /**
     * 获取总量和分页数据
     *
     * @param page 第一页(从0开始)
     * @param size 分页大小
     * @param select 查询返回数据
     * @param cls 需要转换类class,null无需转换
     * @param convertor 转换器(用于字典翻译),null无需转换
     * @param <E>
     * @return
     */
    public static <E> PageResult<E> paging(int page, int size, Select select, Class<E> cls, Convertor<E> convertor){
        validPageSize(page, size);
        PageHelper.startPage(page + 1, size);
        PageResult<E> result = PageResult.convert(new PageInfo(select.select()));
        if(cls != null){
            List<E> models = JSON.parseArray(JSON.toJSONString(result.getModels()), cls);
            result.setModels(models);
        }
        if(convertor != null){
            convertor.convert(result.getModels());
        }
        return result;
    }

    public static void validPageSize(int page, int size){
        ValidatesUtil.isTrue(page < 0, new ServiceException("分页参数page应大于等于0!!!"));
        ValidatesUtil.isTrue(size < 0, new ServiceException("分页参数size应大于0!!!"));
    }

    /**
     * 获取总量和分页数据
     * <p>
     * 页数从0开始
     * 分页格式转换
     * 例子:PageExtHelper.paging(page, size, () -> sysHosServiceExtMapper.getByCondition(dto));
     *
     * @param page
     * @param size
     * @param s
     * @param <T>
     * @return
     */
    public static <T> PageResult<T> paging(int page, int size, Select s){
        validPageSize(page, size);
        PageHelper.startPage(page + 1, size);
        return PageResult.convert(new PageInfo(s.select()));
    }

    /**
     * 字典翻译分页
     *
     * @param page
     * @param size
     * @param s
     * @param con
     * @param <E>
     * @return
     */
    public static <E> PageResult<E> paging(int page, int size, Select s, Convertor<E> con){
        validPageSize(page, size);
        PageResult<E> ePageResult = paging(page, size, s);
        con.convert(ePageResult.getModels());
        return ePageResult;
    }


    /**
     * 查询语句接口
     *
     * @param <T>
     */
    public interface Select<T>{
        List<T> select();
    }

    public interface Convertor<E>{
        void convert(List<E> list);
    }

}
