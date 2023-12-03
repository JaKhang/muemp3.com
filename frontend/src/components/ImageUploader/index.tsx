import UploadIcon from '@mui/icons-material/Upload';
import {Button, Fab, IconButton, InputAdornment, TextField} from "@mui/material";
import {clsx} from "clsx";
import React, {ChangeEvent, useEffect, useRef, useState} from 'react';
import ArrowRightAltIcon from '@mui/icons-material/ArrowRightAlt';

interface ImageUploaderProp {
    onChange?: (e: ChangeEvent<HTMLInputElement>) => any
}

export enum Status {
    LINK,
    FILE,
    ID
}


const ImageUploader = (props: ImageUploaderProp) => {

    const {onChange} = props;
    const inputRef = useRef<HTMLInputElement>(null);
    const [resource, setResource] = useState<File | null>();
    const [url, setUrl] = useState("");
    const [isDragEnter, setIsDragEnter] = useState(false);
    const [status, setStatus] = useState(Status.FILE)

    useEffect(() => {

    }, [url])


    useEffect(() => {
        const handler = (e) => {
            e.preventDefault(); // Disable open image in new tab
        };

        window.addEventListener("dragover", handler);
        window.addEventListener("drop", handler);

        return () => {
            window.removeEventListener("dragover", handler);
            window.removeEventListener("drop", handler);
        };
    }, []);

    const onDragLeave = () => {
        setIsDragEnter(false);
    };

    const onDragEnter = () => {
        setIsDragEnter(true);
    };


    const handleInputChange = (e: React.ChangeEvent<HTMLInputElement>) => {
        if (e.target.files != null) {
            setResource(e.target.files[0]);
            // @ts-ignore
            onChange && onChange(e);
        }


    }

    const onDrop = (e: React.DragEvent<HTMLDivElement>) => {
        setIsDragEnter(false);
        const newFile = e.dataTransfer.files?.[0];
        if (newFile) {
            if (!newFile.type.match("image.*")) {
            } else {
                setResource(newFile);
            }
        }
    };

    const getSrc = () => {
        return resource ? URL.createObjectURL(resource) : "";
    }

    const classes = clsx(
        "uploader",
        Boolean(resource) && "uploader--active",
        isDragEnter && "uploader--drag"
    )


    async function handleDisplayFromLink() {
        if (url) {
            let response = await fetch(url);
            let data = await response.blob();
            let metadata = {
                type: 'image/jpeg'
            };
            let file = new File([data], "test.jpg", metadata);
            setResource(file)
        }
    }

    return (
        <div className={classes}
             style={{backgroundImage: `url(${getSrc()})`}}
             onDrop={onDrop}
             onDragEnter={onDragEnter}
             onDragLeave={onDragLeave}>
            <div className="uploader__item">
                <div className="uploader__text">
                    {status === Status.FILE && "Chọn file hoặc kéo thả"}
                    {status === Status.LINK && "Nhập địa chỉ hình ảnh"}
                    {status === Status.ID && "Chọn file hoặc kéo thả"}
                </div>

                {
                    status === Status.FILE &&
                    (
                        <>
                            <Fab className="uploader__choose-btn" color="primary"
                                 onClick={() => inputRef.current && inputRef.current.click()}>
                                <UploadIcon/>
                                <input type="file" hidden ref={inputRef} onChange={(e) => handleInputChange(e)}
                                       accept="image/*"/>
                            </Fab>
                            <Button
                                variant="text"
                                onClick={() => setStatus(Status.LINK)}
                            >
                                Link
                            </Button>
                        </>
                    )
            }

            {status === Status.LINK &&
                (
                    <>
                        <TextField
                            margin="dense"
                            type="url"
                            value={url}
                            className="uploader__url-input"
                            onChange={(e) => setUrl(e.target.value)}
                            InputProps={{
                                endAdornment: <InputAdornment position="end"><IconButton size="small" onClick={handleDisplayFromLink}><ArrowRightAltIcon/></IconButton></InputAdornment>
                            }}
                        />
                        <Button
                            variant="text"
                            onClick={() => setStatus(Status.FILE)}
                        >
                            File
                        </Button>
                    </>
                )
            }



        </div>
            <div className="uploader__item uploader__item--drag">
                <div className="uploader__text">
                    Kéo ảnh vào đây
                </div>
            </div>
        </div>
)
    ;
};

export default ImageUploader;
