import {
    Box,
    Button,
    Dialog,
    DialogActions,
    DialogContent,
    Paper,
    Typography,
    Table,
    TableBody,
    TableCell,
    TableHead,
    TableRow,
    IconButton
} from "@mui/material";
import AlbumOutlinedIcon from '@mui/icons-material/AlbumOutlined';
import {FileMetadata} from "@models/FileSystemMetadata.ts";
import {useState, useEffect, useCallback} from 'react'
import AudioFileOutlinedIcon from '@mui/icons-material/AudioFileOutlined';
import CloseOutlinedIcon from '@mui/icons-material/CloseOutlined';
import { Theme } from "@mui/material/styles";
import React from 'react';
import FileExplorer from "../FileExplorer";
import {FileType} from "@models/FileType.ts";
import {TableItem} from "../FileExplorer/Item.tsx";
interface AudioBrowserProps {
    multiple?: boolean;
    onChange: (...event: any[]) => void;
    onBlur: () => void;
    value: string;
    disabled?: boolean | undefined;
    name: string;
}


const AudioBrowser = (props: AudioBrowserProps) => {
    const {multiple, onChange, onBlur, name, disabled, value} = props;

    const [open, setOpen] = useState(false);
    const [selectedFiles, setSelectedFile] = useState<FileMetadata[]>([]);

    const handleChangeFiles = (files: FileMetadata[]) => {
        setSelectedFile(files);
    };
    const handleSelect = () => {
        if (multiple) onChange(selectedFiles.map((file) => file.fileId));
        else onChange(selectedFiles[0].id);
        setOpen(false);
    };

    const handleRemove = useCallback(
        (e : MouseEvent, id: string) => {
            e.preventDefault();
            setSelectedFile(selectedFiles.filter((file) => file.id !== id));
        },
        [setSelectedFile]
    );

    const handleCancel = useCallback(() => {
        setOpen(false);
        onBlur();
    }, [onBlur]);


    return (
        <>
            <Paper
                variant="outlined"
                sx={{
                    backgroundRepeat: "no-repeat",
                    backgroundSize: "cover",
                    display: "grid",
                    placeItems: "center",
                    backgroundColor: (theme: Theme) => theme.palette.background.default,
                    cursor: "pointer",
                    padding: 4,
                }}
            >

                {selectedFiles.length ? (
                    <Table>
                        <TableHead>
                            <TableCell width={"2%"} sx={{ paddingX: 0 }}></TableCell>
                            <TableCell>...</TableCell>
                            <TableCell width={"2%"}></TableCell>
                            <TableCell width={"10%"}>Size</TableCell>
                            <TableCell width={"2%"} sx={{ paddingX: 0 }}></TableCell>
                        </TableHead>
                        <TableBody>
                            {selectedFiles.map((file) => (
                                <TableRow key={file.id}>
                                    <TableCell><AudioFileOutlinedIcon/></TableCell>
                                    <TableCell>{file.name}</TableCell>
                                    <TableCell></TableCell>
                                    <TableCell>{file?.size}</TableCell>
                                    <TableCell><IconButton onClick={(e) => handleRemove(e,file.id)}><CloseOutlinedIcon/></IconButton></TableCell>
                                </TableRow>
                            ))}
                        </TableBody>
                    </Table>
                ) : (
                    <Box sx={{ textAlign: "center" }}>
                        <Typography>Click here to add more images.</Typography>
                        <Typography>
                            <IconButton onClick={() => setOpen(true)}>
                                <AlbumOutlinedIcon />
                            </IconButton>
                        </Typography>
                    </Box>
                )}
            </Paper>
            {
                open && (
                    <Dialog
                        open={open}
                        disableEscapeKeyDown
                        fullWidth={true}
                        maxWidth="xl">
                        <DialogContent sx={{padding: 0}}>
                            <FileExplorer
                                multiple={multiple}
                                filter={FileType.AUDIO}
                                onSelectFiles={handleChangeFiles}
                            />
                        </DialogContent>
                        <DialogActions>
                            <Button onClick={handleCancel} color="secondary">
                                Cancel
                            </Button>
                            <Button
                                onClick={handleSelect}
                                color="primary"
                                variant="contained"
                                disabled={selectedFiles.length == 0}>
                                Select
                            </Button>
                        </DialogActions>
                    </Dialog>
                )
            }
        </>
    )

};
export default AudioBrowser;

;
