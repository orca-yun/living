package ag.orca.living.controller;

import ag.orca.living.api.media.LivingMediaLibService;
import ag.orca.living.biz.FilePartService;
import ag.orca.living.common.OrcaPageResult;
import ag.orca.living.common.OrcaResult;
import ag.orca.living.core.entity.filepart.FileInfo;
import ag.orca.living.core.ro.media.LivingMediaLibEditRo;
import ag.orca.living.core.ro.media.LivingMediaLibRo;
import ag.orca.living.core.ro.query.QueryMediaLibRo;
import ag.orca.living.core.vo.media.LivingMediaLibInfoVo;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.Resource;
import org.apache.commons.lang3.tuple.Pair;
import org.apache.dubbo.config.annotation.DubboReference;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/v1/media")
@Tag(name = "媒体库")
public class LivingMediaLibController extends AbstractAdminController {

    @DubboReference
    LivingMediaLibService mediaLibService;

    @Resource
    FilePartService filePartService;

    @Operation(summary = "媒体库列表")
    @GetMapping
    public OrcaPageResult<LivingMediaLibInfoVo> mediaList(QueryMediaLibRo ro) {
        Long orgId = getOrgId();
        Pair<List<LivingMediaLibInfoVo>, Long> pair = mediaLibService.findPageList(orgId, ro);
        return OrcaPageResult.success(pair.getRight(), pair.getLeft());
    }

    @Operation(summary = "上传媒体库内容")
    @PostMapping
    public OrcaResult<Void> saveMedia(@Validated @RequestBody LivingMediaLibRo ro) {
        Optional<FileInfo> optional = filePartService.checkAndGetFileInfo(ro.getFileId());
        Long orgId = getOrgId();
        optional.ifPresent(fileInfo ->
                mediaLibService.saveByManual(orgId, ro.getName(),
                        fileInfo.getFileName(),
                        fileInfo.getBucket(),
                        fileInfo.getPathName()));
        return OrcaResult.success();
    }

    @Operation(summary = "编辑媒体库")
    @PutMapping("/name")
    public OrcaResult<Void> editMediaLibName(@Validated @RequestBody LivingMediaLibEditRo ro) {
        Long orgId = getOrgId();
        mediaLibService.editMediaLibName(orgId, ro);
        return OrcaResult.success();
    }


    @Operation(summary = "删除媒体库")
    @DeleteMapping("/{id}")
    public OrcaResult<Void> removeMedia(@PathVariable("id") Long id) {
        Long orgId = getOrgId();
        mediaLibService.remove(orgId, Collections.singletonList(id));
        return OrcaResult.success();
    }

}
