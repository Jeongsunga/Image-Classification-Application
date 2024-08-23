from flask import Flask, request, jsonify, redirect, url_for
from DB_handler import DBModule
import os, zipfile, subprocess

app = Flask(__name__)
DB = DBModule()

@app.route('/classify-image', methods=['POST'])
def classify_image():
    if 'file' in request.files:
        file = request.files['file']
        if file.filename == '':
            return "No selected file", 400
        
        # 파일 저장
        file_path = os.path.join('UPLOAD_FOLDER', file.filename)
        file.save(file_path)

        with zipfile.ZipFile(file_path, 'r') as zip_ref:
            zip_ref.extractall('UPLOAD_FOLDER')
        if number >= 1 and number <= 4 :
            subprocess.run(['python3', 'main.py'])
        return "main.py executed", 200
    
    else:
        # URL의 쿼리 스트링으로 전달된 데이터 받기
        number = request.args.get('number')
        date = request.args.get('date')

        # 데이터 처리 (예: 출력)
        print(f"Received date: {date}")
        print(f"Received number: {number}")

        # 여기서 받은 데이터를 처리 (예: 데이터베이스 저장, 기타 작업 등)
        return jsonify({
            'status': 'Processed',
            'processed_number': number,
            'processed_date': date
        }), 200
       
    return "Invalid script name", 400
    

@app.route('/receive-data', methods=['POST'])
def receive_data():
    try:
        # JSON 데이터 받기
        data = request.get_json()

        # 숫자와 날짜 데이터 추출
        number = data.get('number')
        date = data.get('date')

        # 데이터 검증 (필요에 따라 추가)
        if number is None or date is None:
            return jsonify({'error': 'Invalid data format'}), 400

        # 여기서 필요한 추가 작업 수행
        # 예: 데이터베이스 저장, 다른 작업 처리 등

        # 다른 route로 데이터를 전달하며 리디렉션
        return redirect(url_for('classify_image', number=number, date=date))
    
    except Exception as e:
        # 에러 처리
        return jsonify({'error': str(e)}), 500


if __name__ == "__main__":
    # 0.0.0.0 으로 해야 같은 와이파이에 폰에서 접속 가능함
    app.run(host="0.0.0.0", debug=True, port=5000)
