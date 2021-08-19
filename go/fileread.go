package _go

import (
	"io/ioutil"
	"fmt"
	"archive/zip"
	"path/filepath"
	"os"
	"io"
)

const readFilePath  = "E://gip_standard//efd1859cb2c94f38aa49828d9f0ca7381621417290410.json"

func main() {
	// readFileOnce()
	// unZipFile("E://gip_standard//demo.zip", "E://gip_standard//demo")
	err := zipFile("E://gip_standard//demo1//", "E://gip_standard//demo21.zip")
	fmt.Println(err)
	//testWalk("E://gip_standard//demo")
}

func readFileOnce()  {
	data, err := ioutil.ReadFile(readFilePath)
	if err != nil {
		fmt.Println(err)
		return
	}
	fmt.Println(string(data))
}

func testWalk(filePath string)  {
	filepath.Walk(filePath, func(path string, info os.FileInfo, err error) error {
		fmt.Println(path + "-----" + info.Name())
		return err
	})
}


func zipFile(srcFile string, destZip string) error {
	// 创建目标zip的write
	zipfile, err := os.Create(destZip)
	if err != nil {
		return err
	}
	defer zipfile.Close()
	archive := zip.NewWriter(zipfile)
	defer archive.Close()
	// 遍历srcFile目录下的文件
	filepath.Walk(srcFile, func(path string, info os.FileInfo, err error) error {
		if err != nil {
			return err
		}
		header, err := zip.FileInfoHeader(info)
		if err != nil {
			return err
		}
		header.Name = path
		if info.IsDir() {
			header.Name += "/"
		} else {
			header.Method = zip.Deflate
		}
		writer, err := archive.CreateHeader(header)
		if err != nil {
			return err
		}
		if ! info.IsDir() {
			file, err := os.Open(path)
			if err != nil {
				return err
			}
			defer file.Close()
			_, err = io.Copy(writer, file)
			fmt.Println(err)
		}
		return err
	})
	return err
}




func unZipFile(zipFile string, destDir string) error {
	// first open zip file
	zipReader, err := zip.OpenReader(zipFile)
	if err != nil {
		return err
	}
	// defer close
	defer zipReader.Close()
	// loop copy file
	for _, f := range zipReader.File {
		fPath := filepath.Join(destDir, f.Name)
		if f.FileInfo().IsDir() {
			os.MkdirAll(fPath, os.ModePerm)
		} else {
			err := os.MkdirAll(filepath.Dir(fPath), os.ModePerm)
			if err != nil {
				return err
			}
			inFile, err := f.Open()
			if err != nil {
				return err
			}
			defer inFile.Close()
			outFile, err := os.OpenFile(fPath, os.O_WRONLY|os.O_CREATE|os.O_TRUNC, f.Mode())
			if err != nil {
				return err
			}
			defer outFile.Close()
			_, err = io.Copy(outFile, inFile)
			if err != nil {
				return err
			}
		}
	}
	return nil
}
