package ag.orca.living.common;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@Getter
@AllArgsConstructor
public enum FileMimeEnum {

    JPEG(new String[]{"image/jpeg"}, ".jpg", "jpg"),
    PNG(new String[]{"image/png"}, ".png", "png"),
    GIF(new String[]{"image/gif"}, ".gif", "gif"),
    BMP(new String[]{"image/bmp"}, ".bmp", "bmp"),
    WEBP(new String[]{"image/webp"}, ".webp", "webp"),

    PDF(new String[]{"application/x-pdf", "application/pdf"}, ".pdf", "pdf"),

    MP4(new String[]{"video/mp4"}, ".mp4", "mp4"),
    _3GP(new String[]{"video/3gpp"}, ".3gp", "3gp"),
    MOV(new String[]{"video/quicktime"}, ".mov", "mov"),
    AVI(new String[]{"video/x-msvideo"}, ".avi", "avi"),
    WMV(new String[]{"video/x-ms-wmv"}, ".wmv", "wmv"),
    MKV(new String[]{"video/x-matroska"}, ".mkv", "mkv"),
    FLV(new String[]{"video/x-flv", "application/x-flash-video", "flv-application/octet-stream", "video/flv"}, ".flv", "flv");


    private final String[] mime;

    private final String suffix;

    private final String type;

    public static final List<FileMimeEnum> IMAGE_MIME_TYPES = Arrays.asList(JPEG, PNG, GIF, BMP, WEBP);

    public static final List<FileMimeEnum> PDF_MIME_TYPES = List.of(PDF);

    public static final List<FileMimeEnum> VIDEO_MIME_TYPES = Arrays.asList(MP4, _3GP, MOV, AVI, WMV, MKV, FLV);


    public static Optional<FileMimeEnum> ofMime(String mime) {
        return Arrays.stream(values()).filter(s -> Arrays.asList(s.mime).contains(mime))
                .findFirst();
    }

    public static Optional<FileMimeEnum> ofImage(String mimeType) {
        Optional<FileMimeEnum> e = ofMime(mimeType);
        return e.filter(IMAGE_MIME_TYPES::contains);
    }

    public static Optional<FileMimeEnum> ofPdf(String mimeType) {
        Optional<FileMimeEnum> e = ofMime(mimeType);
        return e.filter(PDF_MIME_TYPES::contains);
    }

    public static Optional<FileMimeEnum> ofVideo(String mimeType) {
        Optional<FileMimeEnum> e = ofMime(mimeType);
        return e.filter(VIDEO_MIME_TYPES::contains);
    }

    public static Optional<FileMimeEnum> ofSuffix(String suffix) {
        return Arrays.stream(values()).filter(s -> s.getSuffix().equals(suffix))
                .findFirst();
    }

    public static Optional<FileMimeEnum> ofVideoWithSuffix(String suffix) {
        Optional<FileMimeEnum> e = ofSuffix(suffix);
        return e.filter(VIDEO_MIME_TYPES::contains);
    }
}
