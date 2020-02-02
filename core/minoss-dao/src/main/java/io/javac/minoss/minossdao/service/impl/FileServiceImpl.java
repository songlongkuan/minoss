package io.javac.minoss.minossdao.service.impl;

import io.javac.minoss.minossdao.model.FileModel;
import io.javac.minoss.minossdao.mapper.FileMapper;
import io.javac.minoss.minossdao.service.FileService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author pencilso
 * @since 2020-02-02
 */
@Service
public class FileServiceImpl extends ServiceImpl<FileMapper, FileModel> implements FileService {

}
