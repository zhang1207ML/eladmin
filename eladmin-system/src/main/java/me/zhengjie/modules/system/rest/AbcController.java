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
package me.zhengjie.modules.system.rest;

import me.zhengjie.annotation.Log;
import me.zhengjie.modules.system.domain.Abc;
import me.zhengjie.modules.system.service.AbcService;
import me.zhengjie.modules.system.service.dto.AbcQueryCriteria;
import org.springframework.data.domain.Pageable;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import io.swagger.annotations.*;
import java.io.IOException;
import javax.servlet.http.HttpServletResponse;

/**
* @website https://el-admin.vip
* @author Zheng Jie
* @date 2020-10-12
**/
@RestController
@RequiredArgsConstructor
@Api(tags = "api/abc管理")
@RequestMapping("/api/abc")
public class AbcController {

    private final AbcService abcService;

    @Log("导出数据")
    @ApiOperation("导出数据")
    @GetMapping(value = "/download")
    @PreAuthorize("@el.check('abc:list')")
    public void download(HttpServletResponse response, AbcQueryCriteria criteria) throws IOException {
        abcService.download(abcService.queryAll(criteria), response);
    }

    @GetMapping
    @Log("查询api/abc")
    @ApiOperation("查询api/abc")
    @PreAuthorize("@el.check('abc:list')")
    public ResponseEntity<Object> query(AbcQueryCriteria criteria, Pageable pageable){
        return new ResponseEntity<>(abcService.queryAll(criteria,pageable),HttpStatus.OK);
    }

    @PostMapping
    @Log("新增api/abc")
    @ApiOperation("新增api/abc")
    @PreAuthorize("@el.check('abc:add')")
    public ResponseEntity<Object> create(@Validated @RequestBody Abc resources){
        return new ResponseEntity<>(abcService.create(resources),HttpStatus.CREATED);
    }

    @PutMapping
    @Log("修改api/abc")
    @ApiOperation("修改api/abc")
    @PreAuthorize("@el.check('abc:edit')")
    public ResponseEntity<Object> update(@Validated @RequestBody Abc resources){
        abcService.update(resources);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @Log("删除api/abc")
    @ApiOperation("删除api/abc")
    @PreAuthorize("@el.check('abc:del')")
    @DeleteMapping
    public ResponseEntity<Object> delete(@RequestBody Long[] ids) {
        abcService.deleteAll(ids);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}