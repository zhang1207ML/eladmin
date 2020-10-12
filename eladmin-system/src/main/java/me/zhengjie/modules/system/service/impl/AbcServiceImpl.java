/*
*  Copyright 2019-2020 Zheng Jie
*
*  Licensed under the Apache License, Version 2.0 (the "License");
*  you may not use this file except in compliance with the License.
*  You may obtain a copy of the License at
*
*  http://www.apache.org/licenses/LICENSE-2.0
*
*  Unless required by applicable law or agreed to in writing, software
*  distributed under the License is distributed on an "AS IS" BASIS,
*  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
*  See the License for the specific language governing permissions and
*  limitations under the License.
*/
package me.zhengjie.modules.system.service.impl;

import me.zhengjie.modules.system.domain.Abc;
import me.zhengjie.utils.ValidationUtil;
import me.zhengjie.utils.FileUtil;
import lombok.RequiredArgsConstructor;
import me.zhengjie.modules.system.repository.AbcRepository;
import me.zhengjie.modules.system.service.AbcService;
import me.zhengjie.modules.system.service.dto.AbcDto;
import me.zhengjie.modules.system.service.dto.AbcQueryCriteria;
import me.zhengjie.modules.system.service.mapstruct.AbcMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import me.zhengjie.utils.PageUtil;
import me.zhengjie.utils.QueryHelp;
import java.util.List;
import java.util.Map;
import java.io.IOException;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.LinkedHashMap;

/**
* @website https://el-admin.vip
* @description 服务实现
* @author Zheng Jie
* @date 2020-10-12
**/
@Service
@RequiredArgsConstructor
public class AbcServiceImpl implements AbcService {

    private final AbcRepository abcRepository;
    private final AbcMapper abcMapper;

    @Override
    public Map<String,Object> queryAll(AbcQueryCriteria criteria, Pageable pageable){
        Page<Abc> page = abcRepository.findAll((root, criteriaQuery, criteriaBuilder) -> QueryHelp.getPredicate(root,criteria,criteriaBuilder),pageable);
        return PageUtil.toPage(page.map(abcMapper::toDto));
    }

    @Override
    public List<AbcDto> queryAll(AbcQueryCriteria criteria){
        return abcMapper.toDto(abcRepository.findAll((root, criteriaQuery, criteriaBuilder) -> QueryHelp.getPredicate(root,criteria,criteriaBuilder)));
    }

    @Override
    @Transactional
    public AbcDto findById(Long id) {
        Abc abc = abcRepository.findById(id).orElseGet(Abc::new);
        ValidationUtil.isNull(abc.getId(),"Abc","id",id);
        return abcMapper.toDto(abc);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public AbcDto create(Abc resources) {
        return abcMapper.toDto(abcRepository.save(resources));
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void update(Abc resources) {
        Abc abc = abcRepository.findById(resources.getId()).orElseGet(Abc::new);
        ValidationUtil.isNull( abc.getId(),"Abc","id",resources.getId());
        abc.copy(resources);
        abcRepository.save(abc);
    }

    @Override
    public void deleteAll(Long[] ids) {
        for (Long id : ids) {
            abcRepository.deleteById(id);
        }
    }

    @Override
    public void download(List<AbcDto> all, HttpServletResponse response) throws IOException {
        List<Map<String, Object>> list = new ArrayList<>();
        for (AbcDto abc : all) {
            Map<String,Object> map = new LinkedHashMap<>();
            map.put(" field1",  abc.getField1());
            map.put(" field2",  abc.getField2());
            map.put(" createTime",  abc.getCreateTime());
            list.add(map);
        }
        FileUtil.downloadExcel(list, response);
    }
}