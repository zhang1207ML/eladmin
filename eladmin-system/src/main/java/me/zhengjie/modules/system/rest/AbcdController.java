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
import me.zhengjie.modules.system.domain.Abcd;
import me.zhengjie.modules.system.service.AbcdService;
import me.zhengjie.modules.system.service.dto.AbcdQueryCriteria;
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
* @author zwf
* @date 2020-10-14
**/
@RestController
@RequiredArgsConstructor
@Api(tags = "api/abcd管理")
@RequestMapping("/api/abcd")
public class AbcdController {

    private final AbcdService abcdService;

    @Log("导出数据")
    @ApiOperation("导出数据")
    @GetMapping(value = "/download")
    @PreAuthorize("@el.check('abcd:list')")
    public void download(HttpServletResponse response, AbcdQueryCriteria criteria) throws IOException {
        abcdService.download(abcdService.queryAll(criteria), response);
    }

    @GetMapping
    @Log("查询api/abcd")
    @ApiOperation("查询api/abcd")
    @PreAuthorize("@el.check('abcd:list')")
    public ResponseEntity<Object> query(AbcdQueryCriteria criteria, Pageable pageable){
        return new ResponseEntity<>(abcdService.queryAll(criteria,pageable),HttpStatus.OK);
    }

    @PostMapping
    @Log("新增api/abcd")
    @ApiOperation("新增api/abcd")
    @PreAuthorize("@el.check('abcd:add')")
    public ResponseEntity<Object> create(@Validated @RequestBody Abcd resources){
        return new ResponseEntity<>(abcdService.create(resources),HttpStatus.CREATED);
    }

    @PutMapping
    @Log("修改api/abcd")
    @ApiOperation("修改api/abcd")
    @PreAuthorize("@el.check('abcd:edit')")
    public ResponseEntity<Object> update(@Validated @RequestBody Abcd resources){
        abcdService.update(resources);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @Log("删除api/abcd")
        @ApiOperation("删除api/abcd")
        @PreAuthorize("@el.check('abcd:del')")
        @DeleteMapping
        public ResponseEntity<Object> delete(@RequestBody Long[] ids) {
            abcdService.deleteAll(ids);
            return new ResponseEntity<>(HttpStatus.OK);
    }
}