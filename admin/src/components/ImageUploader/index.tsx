import {
    Box,
    Button,
    Dialog,
    DialogActions,
    DialogContent,
    DialogContentText,
    DialogTitle,
    Fab,
    IconButton,
    Input,
    Link,
    Modal,
    styled,
    TextField
} from "@mui/material";
import {clsx} from "clsx";
import React, {useRef, useState} from 'react';
import UploadIcon from '@mui/icons-material/Upload';

import CloseIcon from '@mui/icons-material/Close';

interface ImageUploaderProp{

}

export enum UploadType {
    LINK,
    FILE,
    ID
}


const ImageUploader = (props: ImageUploaderProp) => {

    const inputRef = useRef<HTMLInputElement>(null);
    const [resource, setResource] = useState<File | string | null>();
    const [open, setOpen] = React.useState(false);
    const [uploadType, setUploadType] = useState<UploadType>(UploadType.FILE)

    const handleOpen = () => {
        setOpen(true);
    };
    const handleClose = () => {
        setOpen(false);
    };

    function handleInputChange(e: React.ChangeEvent<HTMLInputElement>) {
        if(e.target.files != null){
            setUploadType(UploadType.FILE)
            setResource(e.target.files[0]);
        }


    }

    const getSrc = () => {
        if (uploadType == UploadType.LINK){
            return resource;
        } else {
            return resource ? URL.createObjectURL(resource) : "";
         }
    }

    const classes = clsx(
        "uploader",
        resource ? "uploader--active" : ""
    )



    return (
        <div className={classes} style={{backgroundImage: `url(${getSrc()})`}}>
            <div className="uploader__item">
                <div className="uploader__text">
                    Chọn file or <Button variant={"text"} size="small" sx={{padding: 0}} onClick={handleOpen}>Link</Button>
                </div>

                <Fab className="uploader__choose-btn" color="primary" onClick={() =>  inputRef.current && inputRef.current.click()}>
                    <UploadIcon/>
                    <input type="file" hidden ref={inputRef} onChange={(e) => handleInputChange(e)}/>
                </Fab>
            </div>

            <Dialog open={open} onClose={handleClose} maxWidth="md" fullWidth sx={{padding: 20}}>
                <DialogTitle>Link ảnh</DialogTitle>
                <DialogContent>
                    <TextField
                        margin="dense"
                        label="Link ảnh"
                        type="url"
                        fullWidth
                        value={uploadType == UploadType.LINK ? resource : ""}
                        onChange={(e) => {setUploadType(UploadType.LINK); setResource(e.target.value)}}
                    />
                </DialogContent>
                <DialogActions>
                    <Button onClick={handleClose} variant="text">OK</Button>
                </DialogActions>
            </Dialog>

        </div>
    );
};

export default ImageUploader;
