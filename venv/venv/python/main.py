'''
정승아, 정호정 졸업 작품 중, 메타 데이터를 활용한 사진 분류 코드를 나타냄
GPS 정보의 위도, 경도를 통한 지역별 구분과 사진 촬영 날짜에 따른 구분 2가지가 있다.
미리 짜놓은 두 가지 코드를 여기서 합쳐서 통합적으로 관리할 수 있도록 함
'''
import location
import pic_date
import sys
import os
import period

def main():
    if len(sys.argv) < 5:
        print("Usage: main.py <필터 번호> <하루/기간> <폴더 이름> <압축한 파일 이름>")
        return
    
    '''
    분류 방법
    option == 1: 얼굴만 인식
    option == 2: 얼굴과 눈 인식
    option == 3: 촬영 날짜
    option == 4: 촬영 위치 
    '''
    print("main 입니다.")
    filterNumber = int(sys.argv[1])  # 첫 번째 인자: 분류 방법
    periodNumber = int(sys.argv[2])  # 두 번째 인자: 날짜 처리 하루(1)/기간(2)
    folderName = sys.argv[3]  # 세 번째 인자: 날짜 처리시, 폴더 이름
    extractFolder = sys.argv[4] # 네 번째 인자 : 압축 해제한 폴더 이름

    os.makedirs("C:\Picture\venv\venv\ClassifyResult", exist_ok=True)

    if filterNumber == 1:
        print("얼굴만 인식합니다.")
        # 얼굴 인식 파이썬 파일 입력

    elif filterNumber == 2:
        print("얼굴과 눈을 인식합니다.")
        # 얼굴&눈 인식 파이썬 파일 입력    
    
    elif filterNumber == 3:
        print("날짜에 따른 분류입니다.")
        if periodNumber == 1:
            pic_date.sortDate(periodNumber, folderName, extractFolder) # 하루
        else:
            period.pic_period(periodNumber, folderName, extractFolder) # 기간


    elif filterNumber == 4:
        print("위치 정보에 따른 분류입니다.")
        location.sortLocation(filterNumber, extractFolder)

    else:
        print("잘못된 접근입니다.")
        return

    print("분류 완료되었습니다.")
    return

if __name__ == "__main__":
    main()